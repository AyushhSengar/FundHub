package com.crowdfund.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain entity representing an immutable audit log entry in FundHub.
 * Tracks security-sensitive events, administrative reviews, and transactions.
 */
public class AuditLog {

    private String id;
    private String actorId;
    private String actorName;
    private String actorRole;
    private String action; // e.g., "CAMPAIGN_CREATED", "CAMPAIGN_APPROVED", "CAMPAIGN_REJECTED", "CONTRIBUTION_RECORDED", "ROLE_UPDATED"
    private String entityType; // e.g., "CAMPAIGN", "DONATION", "USER"
    private String entityId;
    private String details;
    private Instant timestamp;

    public AuditLog() {
        this.timestamp = Instant.now();
    }

    public AuditLog(String id, String actorId, String actorName, String actorRole,
                    String action, String entityType, String entityId, String details, Instant timestamp) {
        this.id = id;
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorRole = actorRole;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorRole() {
        return actorRole;
    }

    public void setActorRole(String actorRole) {
        this.actorRole = actorRole;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(id, auditLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
