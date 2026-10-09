package com.crowdfund.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Domain entity representing a detailed financial & performance report for a single campaign.
 */
public class CampaignReport {

    private String campaignId;
    private String title;
    private String creatorName;
    private BigDecimal targetAmount;
    private BigDecimal collectedAmount;
    private BigDecimal remainingAmount;
    private int percentage;
    private String status;
    private int totalDonationsCount;
    private BigDecimal averageDonationAmount;
    private List<Milestone> milestones;
    private Instant createdAt;

    public CampaignReport() {
    }

    public CampaignReport(String campaignId, String title, String creatorName,
                          BigDecimal targetAmount, BigDecimal collectedAmount, BigDecimal remainingAmount,
                          int percentage, String status, int totalDonationsCount,
                          BigDecimal averageDonationAmount, List<Milestone> milestones, Instant createdAt) {
        this.campaignId = campaignId;
        this.title = title;
        this.creatorName = creatorName;
        this.targetAmount = targetAmount;
        this.collectedAmount = collectedAmount;
        this.remainingAmount = remainingAmount;
        this.percentage = percentage;
        this.status = status;
        this.totalDonationsCount = totalDonationsCount;
        this.averageDonationAmount = averageDonationAmount;
        this.milestones = milestones;
        this.createdAt = createdAt;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(BigDecimal collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalDonationsCount() {
        return totalDonationsCount;
    }

    public void setTotalDonationsCount(int totalDonationsCount) {
        this.totalDonationsCount = totalDonationsCount;
    }

    public BigDecimal getAverageDonationAmount() {
        return averageDonationAmount;
    }

    public void setAverageDonationAmount(BigDecimal averageDonationAmount) {
        this.averageDonationAmount = averageDonationAmount;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
