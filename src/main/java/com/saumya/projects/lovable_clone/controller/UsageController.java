package com.saumya.projects.lovable_clone.controller;

import com.saumya.projects.lovable_clone.dto.usage.PlanLimitResponse;
import com.saumya.projects.lovable_clone.dto.usage.UsageTodayResponse;
import com.saumya.projects.lovable_clone.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usage")
public class UsageController {
    private final UsageService usageService;

    @GetMapping("/today")
    public ResponseEntity<UsageTodayResponse> getUsageToday() {
        Long userId = 1L;
        return ResponseEntity.ok(usageService.getUsageToday(userId));
    }

    @GetMapping("/limits")
    public ResponseEntity<PlanLimitResponse> getPlanLimit() {
        Long userId = 1L;
        return ResponseEntity.ok(usageService.getCurrentPlanLimit(userId));
    }
}
