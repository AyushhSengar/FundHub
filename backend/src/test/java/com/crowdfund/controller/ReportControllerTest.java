package com.crowdfund.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowdfund.dto.CampaignReportResponse;
import com.crowdfund.dto.PlatformReportResponse;
import com.crowdfund.security.AuthUser;
import com.crowdfund.service.ReportService;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    private ReportController controller;

    @BeforeEach
    void setUp() {
        controller = new ReportController(reportService);
    }

    @Test
    void testGetPlatformReport() {
        AuthUser admin = new AuthUser("admin_id", "admin@test.com", "Admin", "ADMIN");
        PlatformReportResponse resp = new PlatformReportResponse(
                Instant.now(), new BigDecimal("5000"), new BigDecimal("10000"), 5,
                Map.of("APPROVED", 3L), Map.of("CREATOR", 2L),
                new BigDecimal("2000"), new BigDecimal("100"), 50.0, List.of()
        );
        when(reportService.generatePlatformReport(admin)).thenReturn(resp);

        PlatformReportResponse res = controller.getPlatformReport(admin);
        assertNotNull(res);
        assertEquals(5, res.totalCampaigns());
        assertEquals(new BigDecimal("5000"), res.totalFundsRaised());
    }

    @Test
    void testGetCampaignReport() {
        AuthUser user = new AuthUser("u1", "u@test.com", "User", "CREATOR");
        CampaignReportResponse resp = new CampaignReportResponse(
                "c1", "Eco", "Creator", new BigDecimal("1000"), new BigDecimal("400"),
                new BigDecimal("600"), 40, "APPROVED", 3, new BigDecimal("133.33"),
                List.of(), Instant.now()
        );
        when(reportService.generateCampaignReport(user, "c1")).thenReturn(resp);

        CampaignReportResponse res = controller.getCampaignReport(user, "c1");
        assertNotNull(res);
        assertEquals("c1", res.campaignId());
        assertEquals(40, res.percentage());
    }
}
