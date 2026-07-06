package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.plans.PlanResponse;
import com.saumya.projects.lovable_clone.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
