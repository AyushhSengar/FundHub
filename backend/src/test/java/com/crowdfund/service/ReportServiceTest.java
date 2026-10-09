package com.crowdfund.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.crowdfund.model.Campaign;
import com.crowdfund.model.CampaignReport;
import com.crowdfund.model.Donation;
import com.crowdfund.model.Milestone;
import com.crowdfund.model.PlatformReport;
import com.crowdfund.model.UserSummary;
import com.google.cloud.firestore.Firestore;

class ReportServiceTest {

    private ReportService reportService;
    private MilestoneService milestoneService;

    @BeforeEach
    void setUp() {
        Firestore db = Mockito.mock(Firestore.class);
        milestoneService = new MilestoneService();
        reportService = new ReportService(db, milestoneService);
    }

    @Test
    void testComputePlatformReport() {
        Campaign c1 = new Campaign("c1", "Camp 1", "Desc", new BigDecimal("1000"), new BigDecimal("1000"), "u1", "Alice", "COMPLETED", "", null, null);
        Campaign c2 = new Campaign("c2", "Camp 2", "Desc", new BigDecimal("2000"), new BigDecimal("500"), "u2", "Bob", "APPROVED", "", null, null);

        UserSummary u1 = new UserSummary("u1", "Alice", "a@test.com", "CREATOR", null);
        UserSummary u2 = new UserSummary("u2", "Bob", "b@test.com", "CREATOR", null);
        UserSummary u3 = new UserSummary("u3", "Charlie", "c@test.com", "CONTRIBUTOR", null);

        Donation d1 = new Donation("d1", "c1", "Camp 1", "u3", "Charlie", "c@test.com", new BigDecimal("1000"), true, null);
        Donation d2 = new Donation("d2", "c2", "Camp 2", "u3", "Charlie", "c@test.com", new BigDecimal("500"), true, null);

        PlatformReport report = reportService.computePlatformReport(List.of(c1, c2), List.of(u1, u2, u3), List.of(d1, d2));

        assertNotNull(report);
        assertEquals(2, report.getTotalCampaigns());
        assertEquals(new BigDecimal("1500"), report.getTotalFundsRaised());
        assertEquals(new BigDecimal("3000"), report.getTotalTargetFunds());
        assertEquals(new BigDecimal("1500.00"), report.getAverageCampaignGoal());
        assertEquals(new BigDecimal("750.00"), report.getAverageContributionAmount());
        assertEquals(50.0, report.getOverallFundingVelocityPercent());
        assertEquals(2, report.getTopFundedCampaigns().size());
    }

    @Test
    void testComputeCampaignReport() {
        Instant now = Instant.now();
        Campaign c = new Campaign("c1", "Eco Drive", "Desc", new BigDecimal("1000.00"), new BigDecimal("500.00"), "u1", "Alice", "APPROVED", "", now, now);
        Donation d1 = new Donation("d1", "c1", "Eco Drive", "u2", "Bob", "b@test.com", new BigDecimal("250.00"), true, now);
        Donation d2 = new Donation("d2", "c1", "Eco Drive", "u3", "Charlie", "c@test.com", new BigDecimal("250.00"), true, now);

        List<Milestone> milestones = milestoneService.calculateMilestones(c);
        CampaignReport report = reportService.computeCampaignReport(c, List.of(d1, d2), milestones);

        assertNotNull(report);
        assertEquals("c1", report.getCampaignId());
        assertEquals("Eco Drive", report.getTitle());
        assertEquals(50, report.getPercentage());
        assertEquals(new BigDecimal("500.00"), report.getRemainingAmount());
        assertEquals(2, report.getTotalDonationsCount());
        assertEquals(new BigDecimal("250.00"), report.getAverageDonationAmount());
        assertEquals(4, report.getMilestones().size());
    }
}
