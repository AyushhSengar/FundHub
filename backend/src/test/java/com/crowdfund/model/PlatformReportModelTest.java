package com.crowdfund.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class PlatformReportModelTest {

    @Test
    void testPlatformReportGettersAndSetters() {
        Instant now = Instant.now();
        PlatformReport report = new PlatformReport();
        report.setGeneratedAt(now);
        report.setTotalFundsRaised(new BigDecimal("10000.00"));
        report.setTotalTargetFunds(new BigDecimal("20000.00"));
        report.setTotalCampaigns(10);
        report.setCampaignsByStatus(Map.of("APPROVED", 8L, "COMPLETED", 2L));
        report.setUsersByRole(Map.of("ADMIN", 1L, "CREATOR", 4L, "CONTRIBUTOR", 10L));
        report.setAverageCampaignGoal(new BigDecimal("2000.00"));
        report.setAverageContributionAmount(new BigDecimal("150.00"));
        report.setOverallFundingVelocityPercent(50.0);
        report.setTopFundedCampaigns(List.of());

        assertEquals(now, report.getGeneratedAt());
        assertEquals(new BigDecimal("10000.00"), report.getTotalFundsRaised());
        assertEquals(new BigDecimal("20000.00"), report.getTotalTargetFunds());
        assertEquals(10, report.getTotalCampaigns());
        assertEquals(8L, report.getCampaignsByStatus().get("APPROVED"));
        assertEquals(50.0, report.getOverallFundingVelocityPercent());
        assertNotNull(report.getTopFundedCampaigns());
    }
}
