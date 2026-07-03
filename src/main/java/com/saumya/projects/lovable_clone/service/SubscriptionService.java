package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.saumya.projects.lovable_clone.dto.subscription.SubscriptionResponse;

public interface SubscriptionService {
    SubscriptionResponse getMySubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(Long userId, CheckoutRequest request);

    PortalResponse createPortalSessionUrl(Long userId);
}
