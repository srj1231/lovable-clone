package com.saumya.projects.lovable_clone.dto.subscription;

import com.saumya.projects.lovable_clone.dto.plans.PlanResponse;
import com.saumya.projects.lovable_clone.enums.SubscriptionStatus;

import java.time.Instant;

public record SubscriptionResponse(
        PlanResponse plan,
        SubscriptionStatus status,
        Instant periodEnd,
        Long tokensUsedThisCycle
) {
}
