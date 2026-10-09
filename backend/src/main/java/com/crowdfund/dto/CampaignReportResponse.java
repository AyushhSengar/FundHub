package com.crowdfund.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.crowdfund.model.CampaignReport;

/**
 * DTO representing a detailed financial & milestone progress report for a campaign.
 */
public record CampaignReportResponse(
        String campaignId,
        String title,
        String creatorName,
        BigDecimal targetAmount,
        BigDecimal collectedAmount,
        BigDecimal remainingAmount,
        int percentage,
        String status,
        int totalDonationsCount,
        BigDecimal averageDonationAmount,
        List<MilestoneResponse> milestones,
        Instant createdAt
) {
    public static CampaignReportResponse fromModel(CampaignReport model) {
        if (model == null) return null;
        List<MilestoneResponse> milestones = model.getMilestones() != null
                ? model.getMilestones().stream().map(MilestoneResponse::fromModel).collect(Collectors.toList())
                : List.of();

        return new CampaignReportResponse(
                model.getCampaignId(),
                model.getTitle(),
                model.getCreatorName(),
                model.getTargetAmount(),
                model.getCollectedAmount(),
                model.getRemainingAmount(),
                model.getPercentage(),
                model.getStatus(),
                model.getTotalDonationsCount(),
                model.getAverageDonationAmount(),
                milestones,
                model.getCreatedAt()
        );
    }
}
