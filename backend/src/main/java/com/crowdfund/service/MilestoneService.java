package com.crowdfund.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crowdfund.model.Campaign;
import com.crowdfund.model.Milestone;

@Service
public class MilestoneService {

    /**
     * Computes the 4 standard crowdfunding milestones (25%, 50%, 75%, 100%) for a campaign.
     */
    public List<Milestone> calculateMilestones(Campaign campaign) {
        List<Milestone> milestones = new ArrayList<>();
        if (campaign == null || campaign.getTargetAmount() == null || campaign.getTargetAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return milestones;
        }

        BigDecimal target = campaign.getTargetAmount();
        BigDecimal collected = campaign.getCollectedAmount() != null ? campaign.getCollectedAmount() : BigDecimal.ZERO;
        Instant now = Instant.now();

        int[] tiers = {25, 50, 75, 100};
        String[] labels = {"Early Spark", "Halfway Mark", "Final Stretch", "Fully Funded"};
        String[] descriptions = {
                "Reached 25% of the goal - strong community start!",
                "Reached 50% of the goal - halfway to victory!",
                "Reached 75% of the goal - almost at the finish line!",
                "Reached 100% of the goal - mission accomplished!"
        };

        for (int i = 0; i < tiers.length; i++) {
            int tier = tiers[i];
            BigDecimal threshold = target.multiply(BigDecimal.valueOf(tier)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            boolean achieved = collected.compareTo(threshold) >= 0;
            Instant achievedTime = achieved ? (campaign.getUpdatedAt() != null ? campaign.getUpdatedAt() : now) : null;

            milestones.add(new Milestone(tier, threshold, achieved, achievedTime, labels[i], descriptions[i]));
        }

        return milestones;
    }
}
