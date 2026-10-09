package com.crowdfund.service;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crowdfund.model.Campaign;
import com.crowdfund.model.Milestone;

class MilestoneServiceTest {

    private MilestoneService milestoneService;

    @BeforeEach
    void setUp() {
        milestoneService = new MilestoneService();
    }

    @Test
    void testCalculateMilestones() {
        Campaign campaign = new Campaign();
        campaign.setId("c1");
        campaign.setTargetAmount(new BigDecimal("1000.00"));
        campaign.setCollectedAmount(new BigDecimal("600.00"));

        List<Milestone> milestones = milestoneService.calculateMilestones(campaign);

        assertNotNull(milestones);
        assertEquals(4, milestones.size());

        // 25% milestone ($250): achieved
        assertEquals(25, milestones.get(0).getTargetPercentage());
        assertEquals(new BigDecimal("250.00"), milestones.get(0).getThresholdAmount());
        assertTrue(milestones.get(0).isAchieved());

        // 50% milestone ($500): achieved
        assertEquals(50, milestones.get(1).getTargetPercentage());
        assertEquals(new BigDecimal("500.00"), milestones.get(1).getThresholdAmount());
        assertTrue(milestones.get(1).isAchieved());

        // 75% milestone ($750): not achieved
        assertEquals(75, milestones.get(2).getTargetPercentage());
        assertEquals(new BigDecimal("750.00"), milestones.get(2).getThresholdAmount());
        assertFalse(milestones.get(2).isAchieved());

        // 100% milestone ($1000): not achieved
        assertEquals(100, milestones.get(3).getTargetPercentage());
        assertEquals(new BigDecimal("1000.00"), milestones.get(3).getThresholdAmount());
        assertFalse(milestones.get(3).isAchieved());
    }

    @Test
    void testCalculateMilestonesNullOrZeroTarget() {
        List<Milestone> empty1 = milestoneService.calculateMilestones(null);
        assertTrue(empty1.isEmpty());

        Campaign zeroCampaign = new Campaign();
        zeroCampaign.setTargetAmount(BigDecimal.ZERO);
        List<Milestone> empty2 = milestoneService.calculateMilestones(zeroCampaign);
        assertTrue(empty2.isEmpty());
    }
}
