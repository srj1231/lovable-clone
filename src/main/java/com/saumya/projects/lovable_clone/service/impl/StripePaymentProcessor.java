package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.saumya.projects.lovable_clone.entity.Plan;
import com.saumya.projects.lovable_clone.entity.User;
import com.saumya.projects.lovable_clone.enums.SubscriptionStatus;
import com.saumya.projects.lovable_clone.exceptions.ResourceNotFoundException;
import com.saumya.projects.lovable_clone.repository.PlanRepository;
import com.saumya.projects.lovable_clone.repository.UserRepository;
import com.saumya.projects.lovable_clone.security.AuthUtil;
import com.saumya.projects.lovable_clone.service.PaymentProcessor;
import com.saumya.projects.lovable_clone.service.SubscriptionService;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentProcessor implements PaymentProcessor {

    private final AuthUtil authUtil;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    private final SubscriptionService subscriptionService;

    @Value("${client.url}")
    private String frontendUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Plan plan = getPlan(request.planId());

        Long userId = authUtil.getCurrentUserId();
        User user = getUser(userId);

        var paramsBuilder = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                            .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                            .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                            .build())
                        .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString());

        try {
            String stripeUserId = user.getStripeCustomerId();

            // Handles both new and existing Stripe customers
            if (stripeUserId == null || stripeUserId.isEmpty()) {
                paramsBuilder.setCustomerEmail(user.getUsername());
            } else {
                paramsBuilder.setCustomer(stripeUserId);
            }
            Session session = Session.create(paramsBuilder.build()); // call stripe api to create checkout session
            return new CheckoutResponse(session.getUrl());
        }
        catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PortalResponse createPortalSessionUrl() {
        return null;
    }

    @Override
    public void processWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {
        // Central dispatcher for Stripe webhook events
        // Routes events to specific handlers based on event type
        log.debug("Handling stripe event {}", type);

        switch (type) { // Stripe event types
            case "checkout.session.completed" -> handleCheckoutSessionCompleted((Session) stripeObject, metadata);
            case "customer.subscription.updated" -> handleCustomerSubscriptionUpdated((Subscription) stripeObject);
            case "customer.subscription.deleted" -> handleCustomerSubscriptionDeleted((Subscription) stripeObject);
            case "invoice.paid" -> handleInvoicePaid((Invoice) stripeObject);
            case "invoice.payment_failed" -> handleInvoicePaymentFailed((Invoice) stripeObject);
            default -> log.debug("Ignoring event {}", type);
        }
    }

    // Stripe event handlers
    private void handleCheckoutSessionCompleted(Session session, Map<String, String> metadata) {
        if(session == null) {
            log.error("Session object is null in handleCheckoutSessionCompleted.");
            return;
        }

        Long userId = Long.parseLong(metadata.get("user_id"));
        Long planId = Long.parseLong(metadata.get("plan_id"));

        String subscriptionId = session.getSubscription();
        String customerId = session.getCustomer();

        var user = getUser(userId);
        if(user.getStripeCustomerId() == null) {
            user.setStripeCustomerId(customerId);
            userRepository.save(user);
        }

        subscriptionService.activateSubscription(userId, planId, subscriptionId, customerId);
    }

    private void handleCustomerSubscriptionUpdated(Subscription subscription) {
        if(subscription == null) {
            log.error("Subscription object is null in handleCustomerSubscriptionUpdated.");
            return;
        }

        SubscriptionStatus status = mapStripeStatusToEnum(subscription.getStatus());
        if(status == null) {
            log.warn("Unknown status {} for subscription {}", subscription.getStatus(), subscription.getId());
            return;
        }

        SubscriptionItem subscriptionItem = subscription.getItems().getData().get(0);
        Instant periodStart = toInstant(subscriptionItem.getCurrentPeriodStart());
        Instant periodEnd = toInstant(subscriptionItem.getCurrentPeriodEnd());

        Long planId = resolvePlanId(subscriptionItem.getPrice());

        subscriptionService.updateSubscription(subscription.getId(), status,
                periodStart, periodEnd, subscription.getCancelAtPeriodEnd(), planId);
    }

    private void handleCustomerSubscriptionDeleted(Subscription subscription) {
        if(subscription == null) {
            log.error("Subscription object is null in handleCustomerSubscriptionDeleted.");
        }

        if (subscription != null) {
            subscriptionService.cancelSubscription(subscription.getId());
        }
    }

    private void handleInvoicePaid(Invoice invoice) {
        String subId = extractSubscriptionId(invoice);
        if(subId == null) log.error("Subscription ID not found in invoice in handleInvoicePaid.");

        try {
            Subscription subscription = null; // call stripe api to get subscription
            if (subId != null) {
                subscription = Subscription.retrieve(subId);
            }
            SubscriptionItem item = null;
            if (subscription != null) {
                item = subscription.getItems().getData().get(0);
            }

            Instant periodStart = null;
            if (item != null) {
                periodStart = toInstant(item.getCurrentPeriodStart());
            }
            Instant periodEnd = null;
            if (item != null) {
                periodEnd = toInstant(item.getCurrentPeriodEnd());
            }

            subscriptionService.renewSubscriptionPeriod(subId, periodStart, periodEnd);

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleInvoicePaymentFailed(Invoice invoice) {
        String subId = extractSubscriptionId(invoice);
        if(subId == null) log.error("Subscription ID not found in invoice in handleInvoicePaymentFailed.");

        subscriptionService.markSubscriptionPastDue(subId);
    }

    private SubscriptionStatus mapStripeStatusToEnum(String status) {
        return switch (status) {
            case "active" -> SubscriptionStatus.ACTIVE;
            case "inactive" -> SubscriptionStatus.INACTIVE;
            case "past_due", "unpaid", "incomplete_expired" -> SubscriptionStatus.PAST_DUE;
            case "canceled" -> SubscriptionStatus.CANCELED;
            case "incomplete" -> SubscriptionStatus.INCOMPLETE;
            default -> {
                log.warn("Unmapped stripe status: {}", status);
                yield null;
            }
        };
    }

    private Long resolvePlanId(Price price) {
        if(price == null || price.getId() == null) return null;
        return planRepository.findByStripePriceId(price.getId())
                .map(Plan::getId)
                .orElse(null);
    }

    private Instant toInstant(Long epoch) {
        // convert epoch seconds to instant
        return epoch == null ? null : Instant.ofEpochSecond(epoch);
    }

    private String extractSubscriptionId(Invoice invoice) {
        var parent = invoice.getParent();
        if(parent == null) return null;

        var subDetails = parent.getSubscriptionDetails();
        return subDetails == null ? null : subDetails.getSubscription();
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId).orElseThrow(
                () -> new ResourceNotFoundException("Plan ", planId.toString())
        );
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User ", userId.toString())
        );
    }
}
