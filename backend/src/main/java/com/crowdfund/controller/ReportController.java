package com.crowdfund.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfund.dto.CampaignReportResponse;
import com.crowdfund.dto.PlatformReportResponse;
import com.crowdfund.security.AuthUser;
import com.crowdfund.security.FirebaseAuthFilter;
import com.crowdfund.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * ADMIN only: Platform performance and financial report.
     */
    @GetMapping("/platform")
    public PlatformReportResponse getPlatformReport(
            @RequestAttribute(FirebaseAuthFilter.ATTRIBUTE) AuthUser user) {

        return reportService.generatePlatformReport(user);
    }

    /**
     * Single campaign report with milestones.
     */
    @GetMapping("/campaigns/{id}")
    public CampaignReportResponse getCampaignReport(
            @RequestAttribute(FirebaseAuthFilter.ATTRIBUTE) AuthUser user,
            @PathVariable String id) {

        return reportService.generateCampaignReport(user, id);
    }
}
