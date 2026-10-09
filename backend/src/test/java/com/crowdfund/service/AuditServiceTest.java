package com.crowdfund.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.crowdfund.exception.AppException;
import com.crowdfund.security.AuthUser;
import com.google.cloud.firestore.Firestore;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private Firestore db;

    private AuditService auditService;

    @BeforeEach
    void setUp() {
        auditService = new AuditService(db);
    }

    @Test
    void testRecordEventDoesNotThrow() {
        AuthUser user = new AuthUser("u1", "user@test.com", "User", "CREATOR");
        assertDoesNotThrow(() -> auditService.recordEvent(user, "TEST_ACTION", "CAMPAIGN", "c1", "Details"));
    }

    @Test
    void testGetRecentLogsRoleCheck() {
        AuthUser nonAdmin = new AuthUser("u1", "user@test.com", "User", "CONTRIBUTOR");
        AppException ex = assertThrows(AppException.class, () -> auditService.getRecentLogs(nonAdmin, 10));
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
    }
}
