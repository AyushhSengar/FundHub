package com.crowdfund.model;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MilestoneModelTest {

    @Test
    void testMilestoneGettersAndEquality() {
        Instant now = Instant.now();
        Milestone m1 = new Milestone(50, new BigDecimal("500.00"), true, now, "Halfway", "50% funded");
        Milestone m2 = new Milestone(50, new BigDecimal("500.00"), true, now, "Halfway", "50% funded");
        Milestone m3 = new Milestone(75, new BigDecimal("750.00"), false, null, "Final Stretch", "75% funded");

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertEquals(50, m1.getTargetPercentage());
        assertEquals(new BigDecimal("500.00"), m1.getThresholdAmount());
        assertTrue(m1.isAchieved());
        assertEquals(now, m1.getAchievedAt());
        assertEquals("Halfway", m1.getLabel());
        assertEquals("50% funded", m1.getDescription());
    }
}
