package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.stripe.model.StripeObject;

import java.util.Map;

public interface PaymentProcessor {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse createPortalSessionUrl();

    void processWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
