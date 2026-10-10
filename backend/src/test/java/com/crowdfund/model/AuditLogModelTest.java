package com.crowdfund.model;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class AuditLogModelTest {

    @Test
    void testAuditLogGettersAndEquality() {
        Instant now = Instant.now();
        AuditLog log1 = new AuditLog("l1", "u1", "Admin", "ADMIN", "APPROVE", "CAMPAIGN", "c1", "Approved", now);
        AuditLog log2 = new AuditLog("l1", "u1", "Admin", "ADMIN", "APPROVE", "CAMPAIGN", "c1", "Approved", now);
        AuditLog log3 = new AuditLog("l2", "u1", "Admin", "ADMIN", "APPROVE", "CAMPAIGN", "c1", "Approved", now);

        assertEquals(log1, log2);
        assertNotEquals(log1, log3);
        assertEquals(log1.hashCode(), log2.hashCode());
        assertEquals("l1", log1.getId());
        assertEquals("u1", log1.getActorId());
        assertEquals("Admin", log1.getActorName());
        assertEquals("ADMIN", log1.getActorRole());
        assertEquals("APPROVE", log1.getAction());
        assertEquals("CAMPAIGN", log1.getEntityType());
        assertEquals("c1", log1.getEntityId());
        assertEquals("Approved", log1.getDetails());
        assertEquals(now, log1.getTimestamp());

        AuditLog defaultLog = new AuditLog();
        assertNotNull(defaultLog.getTimestamp());
    }
}
