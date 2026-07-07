package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.saumya.projects.lovable_clone.enums.SubscriptionStatus;
import com.stripe.model.Subscription;

import java.time.Instant;

public interface SubscriptionService {
    SubscriptionResponse getMySubscription();

    void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId);

    Subscription updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String id);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);
}
