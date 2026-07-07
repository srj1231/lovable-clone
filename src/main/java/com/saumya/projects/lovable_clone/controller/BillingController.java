package com.saumya.projects.lovable_clone.controller;

import com.saumya.projects.lovable_clone.dto.plans.PlanResponse;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.saumya.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.saumya.projects.lovable_clone.service.PaymentProcessor;
import com.saumya.projects.lovable_clone.service.PlanService;
import com.saumya.projects.lovable_clone.service.SubscriptionService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BillingController {
    private final PlanService planService;
    private final SubscriptionService subscriptionService;
    private final PaymentProcessor paymentProcessor;

    @Value("${stripe.webhook}")
    private String stripeWebhookSecret;

    @GetMapping("api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("api/me/subscription")
    public ResponseEntity<SubscriptionResponse> getSubscription() {
        return ResponseEntity.ok(subscriptionService.getMySubscription());
    }

    @PostMapping("api/payments/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(@RequestBody @Valid CheckoutRequest request) {
        return ResponseEntity.ok(paymentProcessor.createCheckoutSessionUrl(request));
    }

    @PostMapping("/api/payments/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal() {
        return ResponseEntity.ok(paymentProcessor.createPortalSessionUrl());
    }

    @PostMapping("/webhooks/payment")
    public ResponseEntity<String> handlePaymentWebhook(@RequestBody String payload,
                                                       @RequestHeader("Stripe-Signature") String stripeSignature) {
        try {
            Event event = Webhook.constructEvent(payload, stripeSignature, stripeWebhookSecret);

            EventDataObjectDeserializer eventData = event.getDataObjectDeserializer();
            StripeObject stripeObject;

            if(eventData.getObject().isPresent()) {
                stripeObject = eventData.getObject().get();
            } else {
                // fallback: deserialize from JSON
                try {
                    stripeObject = eventData.deserializeUnsafe();
                    if(stripeObject == null) {
                        log.warn("Could not deserialize stripe object: {}", event.getType());
                        return ResponseEntity.ok().build();
                    }
                } catch (Exception e) {
                    log.error("Unsafe deserialization failed for stripe object {} : {}", event.getType(), e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deserialization failed.");
                }
            }

            Map<String, String> metadata = new HashMap<>();
            if(stripeObject instanceof Session session) {
                metadata = session.getMetadata();
            }

            paymentProcessor.processWebhookEvent(event.getType(), stripeObject, metadata);
            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }
    }
}
