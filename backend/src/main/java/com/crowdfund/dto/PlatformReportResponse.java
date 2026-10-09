package com.crowdfund.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crowdfund.model.PlatformReport;

/**
 * DTO for comprehensive platform financial report.
 */
public record PlatformReportResponse(
        Instant generatedAt,
        BigDecimal totalFundsRaised,
        BigDecimal totalTargetFunds,
        long totalCampaigns,
        Map<String, Long> campaignsByStatus,
        Map<String, Long> usersByRole,
        BigDecimal averageCampaignGoal,
        BigDecimal averageContributionAmount,
        double overallFundingVelocityPercent,
        List<CampaignResponse> topFundedCampaigns
) {
    public static PlatformReportResponse fromModel(PlatformReport model) {
        if (model == null) return null;
        List<CampaignResponse> top = model.getTopFundedCampaigns() != null
                ? model.getTopFundedCampaigns().stream().map(CampaignResponse::fromModel).collect(Collectors.toList())
                : List.of();

        return new PlatformReportResponse(
                model.getGeneratedAt(),
                model.getTotalFundsRaised(),
                model.getTotalTargetFunds(),
                model.getTotalCampaigns(),
                model.getCampaignsByStatus(),
                model.getUsersByRole(),
                model.getAverageCampaignGoal(),
                model.getAverageContributionAmount(),
                model.getOverallFundingVelocityPercent(),
                top
        );
    }
}
