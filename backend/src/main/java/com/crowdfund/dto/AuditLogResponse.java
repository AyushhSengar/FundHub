package com.crowdfund.dto;

import java.time.Instant;

import com.crowdfund.model.AuditLog;

/**
 * DTO representing an immutable audit log entry for administrative inspection.
 */
public record AuditLogResponse(
        String id,
        String actorId,
        String actorName,
        String actorRole,
        String action,
        String entityType,
        String entityId,
        String details,
        Instant timestamp
) {
    public static AuditLogResponse fromModel(AuditLog model) {
        if (model == null) return null;
        return new AuditLogResponse(
                model.getId(),
                model.getActorId(),
                model.getActorName(),
                model.getActorRole(),
                model.getAction(),
                model.getEntityType(),
                model.getEntityId(),
                model.getDetails(),
                model.getTimestamp()
        );
    }
}
