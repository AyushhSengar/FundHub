package com.crowdfund.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.crowdfund.model.Milestone;

/**
 * DTO representing a campaign funding milestone.
 */
public record MilestoneResponse(
        int targetPercentage,
        BigDecimal thresholdAmount,
        boolean achieved,
        Instant achievedAt,
        String label,
        String description
) {
    public static MilestoneResponse fromModel(Milestone model) {
        if (model == null) return null;
        return new MilestoneResponse(
                model.getTargetPercentage(),
                model.getThresholdAmount(),
                model.isAchieved(),
                model.getAchievedAt(),
                model.getLabel(),
                model.getDescription()
        );
    }
}
