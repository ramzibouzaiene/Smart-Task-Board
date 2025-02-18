package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}/assign-role")
    public ResponseEntity<String> assignRole(
            @PathVariable Long userId,
            @RequestParam String roleName) {
        userService.assignRoleToUser(userId, roleName);
        log.info("Role assigned successfully");
        return ResponseEntity.ok("Role assigned successfully");
    }
}
