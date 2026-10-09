package com.crowdfund.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.crowdfund.dto.CampaignReportResponse;
import com.crowdfund.dto.PlatformReportResponse;
import com.crowdfund.exception.AppException;
import com.crowdfund.model.Campaign;
import com.crowdfund.model.CampaignReport;
import com.crowdfund.model.Donation;
import com.crowdfund.model.Milestone;
import com.crowdfund.model.PlatformReport;
import com.crowdfund.model.UserSummary;
import com.crowdfund.security.AuthUser;
import com.crowdfund.util.FirestoreSupport;
import com.crowdfund.util.Text;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@Service
public class ReportService {

    private final Firestore db;
    private final MilestoneService milestoneService;

    public ReportService(Firestore db, MilestoneService milestoneService) {
        this.db = db;
        this.milestoneService = milestoneService;
    }

    /**
     * ADMIN only: Generates full platform performance analytics report.
     */
    public PlatformReportResponse generatePlatformReport(AuthUser caller) {
        caller.requireRole("ADMIN");

        List<QueryDocumentSnapshot> campaignDocs = FirestoreSupport.await(db.collection("campaigns").get()).getDocuments();
        List<QueryDocumentSnapshot> userDocs = FirestoreSupport.await(db.collection("users").get()).getDocuments();
        List<QueryDocumentSnapshot> donationDocs = FirestoreSupport.await(db.collection("donations").get()).getDocuments();

        List<Campaign> campaigns = campaignDocs.stream().map(this::mapDocToCampaign).collect(Collectors.toList());
        List<UserSummary> users = userDocs.stream().map(this::mapDocToUser).collect(Collectors.toList());
        List<Donation> donations = donationDocs.stream().map(this::mapDocToDonation).collect(Collectors.toList());

        PlatformReport report = computePlatformReport(campaigns, users, donations);
        return PlatformReportResponse.fromModel(report);
    }

    /**
     * Retrieves a detailed report for a single campaign including milestones.
     */
    public CampaignReportResponse generateCampaignReport(AuthUser caller, String campaignId) {
        Text.requireId(campaignId);

        DocumentSnapshot cDoc = FirestoreSupport.await(db.collection("campaigns").document(campaignId).get());
        if (!cDoc.exists()) {
            throw new AppException(HttpStatus.NOT_FOUND, "Campaign not found.");
        }

        Campaign campaign = mapDocToCampaign(cDoc);
        List<QueryDocumentSnapshot> donationDocs = FirestoreSupport.await(
                db.collection("donations")
                        .whereEqualTo("campaignId", campaignId)
                        .get()
        ).getDocuments();

        List<Donation> donations = donationDocs.stream().map(this::mapDocToDonation).collect(Collectors.toList());
        List<Milestone> milestones = milestoneService.calculateMilestones(campaign);

        CampaignReport report = computeCampaignReport(campaign, donations, milestones);
        return CampaignReportResponse.fromModel(report);
    }

    // --- Domain Calculation Helpers (Unit Testable) ---

    public PlatformReport computePlatformReport(List<Campaign> campaigns, List<UserSummary> users, List<Donation> donations) {
        BigDecimal totalRaised = BigDecimal.ZERO;
        BigDecimal totalTarget = BigDecimal.ZERO;
        Map<String, Long> statusMap = new HashMap<>();
        Map<String, Long> roleMap = new HashMap<>();

        for (Campaign c : campaigns) {
            String status = c.getStatus() != null ? c.getStatus().toUpperCase() : "PENDING";
            statusMap.put(status, statusMap.getOrDefault(status, 0L) + 1);
            if (c.getCollectedAmount() != null) totalRaised = totalRaised.add(c.getCollectedAmount());
            if (c.getTargetAmount() != null) totalTarget = totalTarget.add(c.getTargetAmount());
        }

        for (UserSummary u : users) {
            String role = u.getRole() != null ? u.getRole().toUpperCase() : "CONTRIBUTOR";
            roleMap.put(role, roleMap.getOrDefault(role, 0L) + 1);
        }

        BigDecimal avgGoal = campaigns.isEmpty() ? BigDecimal.ZERO : totalTarget.divide(BigDecimal.valueOf(campaigns.size()), 2, RoundingMode.HALF_UP);
        BigDecimal avgDonation = donations.isEmpty() ? BigDecimal.ZERO : totalRaised.divide(BigDecimal.valueOf(donations.size()), 2, RoundingMode.HALF_UP);
        double velocity = totalTarget.compareTo(BigDecimal.ZERO) > 0
                ? totalRaised.divide(totalTarget, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0;

        List<Campaign> topFunded = campaigns.stream()
                .sorted((a, b) -> (b.getCollectedAmount() != null ? b.getCollectedAmount() : BigDecimal.ZERO)
                        .compareTo(a.getCollectedAmount() != null ? a.getCollectedAmount() : BigDecimal.ZERO))
                .limit(5)
                .collect(Collectors.toList());

        return new PlatformReport(
                Instant.now(),
                totalRaised,
                totalTarget,
                campaigns.size(),
                statusMap,
                roleMap,
                avgGoal,
                avgDonation,
                velocity,
                topFunded
        );
    }

    public CampaignReport computeCampaignReport(Campaign campaign, List<Donation> donations, List<Milestone> milestones) {
        BigDecimal collected = campaign.getCollectedAmount() != null ? campaign.getCollectedAmount() : BigDecimal.ZERO;
        BigDecimal target = campaign.getTargetAmount() != null ? campaign.getTargetAmount() : BigDecimal.ZERO;
        BigDecimal remaining = campaign.calculateRemainingAmount();
        int percentage = campaign.calculatePercentage();

        BigDecimal avgDonation = donations.isEmpty() ? BigDecimal.ZERO
                : collected.divide(BigDecimal.valueOf(donations.size()), 2, RoundingMode.HALF_UP);

        return new CampaignReport(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getCreatorName(),
                target,
                collected,
                remaining,
                percentage,
                campaign.getStatus(),
                donations.size(),
                avgDonation,
                milestones,
                campaign.getCreatedAt()
        );
    }

    private Campaign mapDocToCampaign(DocumentSnapshot doc) {
        Campaign c = new Campaign();
        c.setId(doc.getId());
        c.setTitle(doc.getString("title"));
        c.setDescription(doc.getString("description"));
        c.setCreatorId(doc.getString("creatorId"));
        c.setCreatorName(doc.getString("creatorName"));
        c.setStatus(doc.getString("status"));
        c.setRejectionReason(doc.getString("rejectionReason"));
        Double target = doc.getDouble("targetAmount");
        Double collected = doc.getDouble("collectedAmount");
        c.setTargetAmount(target != null ? BigDecimal.valueOf(target) : BigDecimal.ZERO);
        c.setCollectedAmount(collected != null ? BigDecimal.valueOf(collected) : BigDecimal.ZERO);
        Timestamp created = doc.getTimestamp("createdAt");
        if (created != null) c.setCreatedAt(created.toDate().toInstant());
        Timestamp updated = doc.getTimestamp("updatedAt");
        if (updated != null) c.setUpdatedAt(updated.toDate().toInstant());
        return c;
    }

    private UserSummary mapDocToUser(DocumentSnapshot doc) {
        UserSummary u = new UserSummary();
        u.setUid(doc.getId());
        u.setName(doc.getString("name"));
        u.setEmail(doc.getString("email"));
        u.setRole(doc.getString("ROLE"));
        Timestamp created = doc.getTimestamp("createdAt");
        if (created != null) u.setCreatedAt(created.toDate().toInstant());
        return u;
    }

    private Donation mapDocToDonation(DocumentSnapshot doc) {
        Donation d = new Donation();
        d.setId(doc.getId());
        d.setCampaignId(doc.getString("campaignId"));
        d.setCampaignTitle(doc.getString("campaignTitle"));
        d.setContributorId(doc.getString("contributorId"));
        d.setContributorName(doc.getString("contributorName"));
        d.setContributorEmail(doc.getString("contributorEmail"));
        Double amt = doc.getDouble("amount");
        d.setAmount(amt != null ? BigDecimal.valueOf(amt) : BigDecimal.ZERO);
        Boolean sim = doc.getBoolean("simulated");
        d.setSimulated(sim != null ? sim : true);
        Timestamp created = doc.getTimestamp("createdAt");
        if (created != null) d.setCreatedAt(created.toDate().toInstant());
        return d;
    }
}
