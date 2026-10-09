package com.crowdfund.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfund.dto.AuditLogResponse;
import com.crowdfund.security.AuthUser;
import com.crowdfund.security.FirebaseAuthFilter;
import com.crowdfund.service.AuditService;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * ADMIN only: Lists immutable audit events.
     */
    @GetMapping
    public List<AuditLogResponse> getLogs(
            @RequestAttribute(FirebaseAuthFilter.ATTRIBUTE) AuthUser user,
            @RequestParam(defaultValue = "50") int limit) {

        return auditService.getRecentLogs(user, limit);
    }
}
