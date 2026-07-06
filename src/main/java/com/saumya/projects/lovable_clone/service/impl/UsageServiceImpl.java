package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.usage.PlanLimitResponse;
import com.saumya.projects.lovable_clone.dto.usage.UsageTodayResponse;
import com.saumya.projects.lovable_clone.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {
    @Override
    public UsageTodayResponse getUsageToday(Long userId) {
        return null;
    }

    @Override
    public PlanLimitResponse getCurrentPlanLimit(Long userId) {
        return null;
    }
}
