package com.crowdfund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.crowdfund.dto.RegisterRequest;
import com.crowdfund.dto.RoleRequest;
import com.crowdfund.exception.AppException;
import com.crowdfund.security.AuthUser;
import com.google.cloud.firestore.Firestore;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private Firestore db;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("register rejects ADMIN role self-selection")
    void testRegisterAdminBlocked() {
        AuthUser caller = new AuthUser("uid1", "user@test.com", "User", null);
        RegisterRequest req = new RegisterRequest("User Name", "ADMIN");

        AppException ex = assertThrows(AppException.class,
                () -> userService.register(caller, req));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    @DisplayName("register requires a non-empty name")
    void testRegisterNameValidation() {
        AuthUser caller = new AuthUser("uid1", "user@test.com", "User", null);
        RegisterRequest req = new RegisterRequest("", "CREATOR");

        AppException ex = assertThrows(AppException.class,
                () -> userService.register(caller, req));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    @DisplayName("changeRole blocks non-ADMIN callers")
    void testChangeRoleRestriction() {
        AuthUser creator = new AuthUser("uid1", "creator@test.com", "Creator", "CREATOR");
        RoleRequest req = new RoleRequest("CONTRIBUTOR");

        AppException ex = assertThrows(AppException.class,
                () -> userService.changeRole(creator, "targetUid", req));
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
    }

    @Test
    @DisplayName("changeRole prevents admin from editing their own role")
    void testChangeRoleSelfEditBlocked() {
        AuthUser admin = new AuthUser("adminUid", "admin@test.com", "Admin", "ADMIN");
        RoleRequest req = new RoleRequest("CONTRIBUTOR");

        AppException ex = assertThrows(AppException.class,
                () -> userService.changeRole(admin, "adminUid", req));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }
}
