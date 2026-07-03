package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.saumya.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.saumya.projects.lovable_clone.dto.subscription.PortalResponse;
import com.saumya.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.saumya.projects.lovable_clone.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionResponse getMySubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(Long userId, CheckoutRequest request) {
        return null;
    }

    @Override
    public PortalResponse createPortalSessionUrl(Long userId) {
        return null;
    }
}
