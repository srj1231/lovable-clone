package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.plans.PlanResponse;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();
}
