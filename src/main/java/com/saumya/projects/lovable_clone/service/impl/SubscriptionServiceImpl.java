package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.saumya.projects.lovable_clone.enums.SubscriptionStatus;
import com.saumya.projects.lovable_clone.service.SubscriptionService;
import com.stripe.model.Subscription;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionResponse getMySubscription() {
        return null;
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {

    }

    @Override
    public Subscription updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {
        return null;
    }

    @Override
    public void cancelSubscription(String id) {

    }

    @Override
    public void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd) {

    }

    @Override
    public void markSubscriptionPastDue(String subId) {

    }
}
