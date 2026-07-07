package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.saumya.projects.lovable_clone.entity.Plan;
import com.saumya.projects.lovable_clone.exceptions.ResourceNotFoundException;
import com.saumya.projects.lovable_clone.repository.PlanRepository;
import com.saumya.projects.lovable_clone.security.AuthUtil;
import com.saumya.projects.lovable_clone.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
@RequiredArgsConstructor
public class StripePaymentProcessor implements PaymentProcessor {

    private final AuthUtil authUtil;
    private final PlanRepository planRepository;

    @Value("${client.url}")
    private String frontendUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Plan plan = planRepository.findById(request.planId()).orElseThrow(
                () -> new ResourceNotFoundException("Plan ", request.planId().toString())
        );

        Long userId = authUtil.getCurrentUserId();

        SessionCreateParams params = SessionCreateParams.builder()
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
                .putMetadata("plan_id", plan.getId().toString())
                .build();

        try {
            Session session = Session.create(params); // call stripe api to create checkout session
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
}
