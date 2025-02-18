package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}/assign-role")
    public ResponseEntity<String> assignRole(
            @PathVariable Long userId,
            @RequestParam String roleName) {
        userService.assignRoleToUser(userId, roleName);
        return ResponseEntity.ok("Role assigned successfully");
    }

    @PostMapping("/testAdmin")
    public ResponseEntity<String> seyHello() {
        return ResponseEntity.ok("Hello from Admin Controller!");
    }
}
