package com.crowdfund.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class CampaignReportModelTest {

    @Test
    void testCampaignReportGettersAndSetters() {
        Instant now = Instant.now();
        CampaignReport report = new CampaignReport();
        report.setCampaignId("c1");
        report.setTitle("Project");
        report.setCreatorName("Creator");
        report.setTargetAmount(new BigDecimal("1000.00"));
        report.setCollectedAmount(new BigDecimal("600.00"));
        report.setRemainingAmount(new BigDecimal("400.00"));
        report.setPercentage(60);
        report.setStatus("APPROVED");
        report.setTotalDonationsCount(4);
        report.setAverageDonationAmount(new BigDecimal("150.00"));
        report.setMilestones(List.of());
        report.setCreatedAt(now);

        assertEquals("c1", report.getCampaignId());
        assertEquals("Project", report.getTitle());
        assertEquals("Creator", report.getCreatorName());
        assertEquals(new BigDecimal("1000.00"), report.getTargetAmount());
        assertEquals(new BigDecimal("600.00"), report.getCollectedAmount());
        assertEquals(new BigDecimal("400.00"), report.getRemainingAmount());
        assertEquals(60, report.getPercentage());
        assertEquals("APPROVED", report.getStatus());
        assertEquals(4, report.getTotalDonationsCount());
        assertEquals(new BigDecimal("150.00"), report.getAverageDonationAmount());
        assertNotNull(report.getMilestones());
        assertEquals(now, report.getCreatedAt());
    }
}
