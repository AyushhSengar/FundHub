package com.crowdfund.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Domain entity representing a funding milestone achieved by a campaign.
 */
public class Milestone {

    private int targetPercentage; // 25, 50, 75, 100
    private BigDecimal thresholdAmount;
    private boolean achieved;
    private Instant achievedAt;
    private String label;
    private String description;

    public Milestone() {
    }

    public Milestone(int targetPercentage, BigDecimal thresholdAmount, boolean achieved,
                     Instant achievedAt, String label, String description) {
        this.targetPercentage = targetPercentage;
        this.thresholdAmount = thresholdAmount;
        this.achieved = achieved;
        this.achievedAt = achievedAt;
        this.label = label;
        this.description = description;
    }

    public int getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(int targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public Instant getAchievedAt() {
        return achievedAt;
    }

    public void setAchievedAt(Instant achievedAt) {
        this.achievedAt = achievedAt;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Milestone milestone = (Milestone) o;
        return targetPercentage == milestone.targetPercentage && Objects.equals(label, milestone.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetPercentage, label);
    }
}
