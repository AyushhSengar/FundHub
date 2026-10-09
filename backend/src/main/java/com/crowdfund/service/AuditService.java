package com.crowdfund.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.crowdfund.dto.AuditLogResponse;
import com.crowdfund.model.AuditLog;
import com.crowdfund.security.AuthUser;
import com.crowdfund.util.FirestoreSupport;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@Service
public class AuditService {

    private final Firestore db;

    public AuditService(Firestore db) {
        this.db = db;
    }

    /**
     * Records an immutable audit event in Firestore.
     */
    public void recordEvent(AuthUser actor, String action, String entityType, String entityId, String details) {
        try {
            DocumentReference ref = db.collection("audit_logs").document();
            Map<String, Object> data = new HashMap<>();
            data.put("actorId", actor != null ? actor.uid() : "SYSTEM");
            data.put("actorName", actor != null ? actor.displayName() : "System");
            data.put("actorRole", actor != null ? actor.role() : "SYSTEM");
            data.put("action", action);
            data.put("entityType", entityType);
            data.put("entityId", entityId);
            data.put("details", details);
            data.put("timestamp", FieldValue.serverTimestamp());

            FirestoreSupport.await(ref.set(data));
        } catch (Exception ignored) {
            // Audit failures should not abort main transactions in demo mode
        }
    }

    /**
     * ADMIN only: Retrieves recent audit logs sorted by timestamp descending.
     */
    public List<AuditLogResponse> getRecentLogs(AuthUser caller, int limit) {
        caller.requireRole("ADMIN");

        int safeLimit = Math.min(Math.max(1, limit), 100);
        List<QueryDocumentSnapshot> docs = FirestoreSupport.await(
                db.collection("audit_logs")
                        .limit(safeLimit)
                        .get()
        ).getDocuments();

        List<AuditLog> logs = docs.stream().map(doc -> {
            AuditLog log = new AuditLog();
            log.setId(doc.getId());
            log.setActorId(doc.getString("actorId"));
            log.setActorName(doc.getString("actorName"));
            log.setActorRole(doc.getString("actorRole"));
            log.setAction(doc.getString("action"));
            log.setEntityType(doc.getString("entityType"));
            log.setEntityId(doc.getString("entityId"));
            log.setDetails(doc.getString("details"));
            Timestamp ts = doc.getTimestamp("timestamp");
            if (ts != null) {
                log.setTimestamp(ts.toDate().toInstant());
            }
            return log;
        }).sorted((a, b) -> {
            Instant tA = a.getTimestamp() != null ? a.getTimestamp() : Instant.EPOCH;
            Instant tB = b.getTimestamp() != null ? b.getTimestamp() : Instant.EPOCH;
            return tB.compareTo(tA);
        }).collect(Collectors.toList());

        return logs.stream().map(AuditLogResponse::fromModel).collect(Collectors.toList());
    }
}
