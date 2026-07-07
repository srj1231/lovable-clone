package com.saumya.projects.lovable_clone.controller;

import com.saumya.projects.lovable_clone.dto.plans.PlanResponse;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.saumya.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.saumya.projects.lovable_clone.service.PaymentProcessor;
import com.saumya.projects.lovable_clone.service.PlanService;
import com.saumya.projects.lovable_clone.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingController {
    private final PlanService planService;
    private final SubscriptionService subscriptionService;
    private final PaymentProcessor paymentProcessor;

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
}
