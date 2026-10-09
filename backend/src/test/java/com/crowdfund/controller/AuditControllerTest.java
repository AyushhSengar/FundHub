package com.crowdfund.controller;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowdfund.dto.AuditLogResponse;
import com.crowdfund.security.AuthUser;
import com.crowdfund.service.AuditService;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    private AuditService auditService;

    private AuditController controller;

    @BeforeEach
    void setUp() {
        controller = new AuditController(auditService);
    }

    @Test
    void testGetLogs() {
        AuthUser admin = new AuthUser("admin_id", "admin@test.com", "Admin", "ADMIN");
        AuditLogResponse log = new AuditLogResponse("l1", "admin_id", "Admin", "ADMIN", "CAMPAIGN_APPROVED", "CAMPAIGN", "c1", "Details", Instant.now());
        when(auditService.getRecentLogs(admin, 50)).thenReturn(List.of(log));

        List<AuditLogResponse> res = controller.getLogs(admin, 50);
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("CAMPAIGN_APPROVED", res.get(0).action());
    }
}
