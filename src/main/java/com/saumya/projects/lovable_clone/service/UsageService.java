package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.usage.PlanLimitResponse;
import com.saumya.projects.lovable_clone.dto.usage.UsageTodayResponse;

public interface UsageService {
    UsageTodayResponse getUsageToday();

    PlanLimitResponse getCurrentPlanLimit();
}
