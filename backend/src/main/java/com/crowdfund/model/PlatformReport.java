package com.crowdfund.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Domain entity representing an analytical performance report of the platform.
 */
public class PlatformReport {

    private Instant generatedAt;
    private BigDecimal totalFundsRaised;
    private BigDecimal totalTargetFunds;
    private long totalCampaigns;
    private Map<String, Long> campaignsByStatus;
    private Map<String, Long> usersByRole;
    private BigDecimal averageCampaignGoal;
    private BigDecimal averageContributionAmount;
    private double overallFundingVelocityPercent;
    private List<Campaign> topFundedCampaigns;

    public PlatformReport() {
        this.generatedAt = Instant.now();
    }

    public PlatformReport(Instant generatedAt, BigDecimal totalFundsRaised, BigDecimal totalTargetFunds,
                          long totalCampaigns, Map<String, Long> campaignsByStatus, Map<String, Long> usersByRole,
                          BigDecimal averageCampaignGoal, BigDecimal averageContributionAmount,
                          double overallFundingVelocityPercent, List<Campaign> topFundedCampaigns) {
        this.generatedAt = generatedAt;
        this.totalFundsRaised = totalFundsRaised;
        this.totalTargetFunds = totalTargetFunds;
        this.totalCampaigns = totalCampaigns;
        this.campaignsByStatus = campaignsByStatus;
        this.usersByRole = usersByRole;
        this.averageCampaignGoal = averageCampaignGoal;
        this.averageContributionAmount = averageContributionAmount;
        this.overallFundingVelocityPercent = overallFundingVelocityPercent;
        this.topFundedCampaigns = topFundedCampaigns;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }

    public BigDecimal getTotalFundsRaised() {
        return totalFundsRaised;
    }

    public void setTotalFundsRaised(BigDecimal totalFundsRaised) {
        this.totalFundsRaised = totalFundsRaised;
    }

    public BigDecimal getTotalTargetFunds() {
        return totalTargetFunds;
    }

    public void setTotalTargetFunds(BigDecimal totalTargetFunds) {
        this.totalTargetFunds = totalTargetFunds;
    }

    public long getTotalCampaigns() {
        return totalCampaigns;
    }

    public void setTotalCampaigns(long totalCampaigns) {
        this.totalCampaigns = totalCampaigns;
    }

    public Map<String, Long> getCampaignsByStatus() {
        return campaignsByStatus;
    }

    public void setCampaignsByStatus(Map<String, Long> campaignsByStatus) {
        this.campaignsByStatus = campaignsByStatus;
    }

    public Map<String, Long> getUsersByRole() {
        return usersByRole;
    }

    public void setUsersByRole(Map<String, Long> usersByRole) {
        this.usersByRole = usersByRole;
    }

    public BigDecimal getAverageCampaignGoal() {
        return averageCampaignGoal;
    }

    public void setAverageCampaignGoal(BigDecimal averageCampaignGoal) {
        this.averageCampaignGoal = averageCampaignGoal;
    }

    public BigDecimal getAverageContributionAmount() {
        return averageContributionAmount;
    }

    public void setAverageContributionAmount(BigDecimal averageContributionAmount) {
        this.averageContributionAmount = averageContributionAmount;
    }

    public double getOverallFundingVelocityPercent() {
        return overallFundingVelocityPercent;
    }

    public void setOverallFundingVelocityPercent(double overallFundingVelocityPercent) {
        this.overallFundingVelocityPercent = overallFundingVelocityPercent;
    }

    public List<Campaign> getTopFundedCampaigns() {
        return topFundedCampaigns;
    }

    public void setTopFundedCampaigns(List<Campaign> topFundedCampaigns) {
        this.topFundedCampaigns = topFundedCampaigns;
    }
}
