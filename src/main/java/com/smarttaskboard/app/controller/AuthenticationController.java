package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.dto.AuthenticationRequestDto;
import com.smarttaskboard.app.dto.AuthenticationResponseDto;
import com.smarttaskboard.app.dto.CreateAdminRequestDto;
import com.smarttaskboard.app.dto.RegisterRequestDto;
import com.smarttaskboard.app.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth-service")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            AuthenticationResponseDto newUser = authenticationService.register(registerRequest);
            log.info("New User {} registered", registerRequest.getUsername());
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            log.error("Error while creating new user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto authenticationRequest) {
        try {
            AuthenticationResponseDto loggedUser = authenticationService.authenticate(authenticationRequest);
            log.info("User with email {} is logged in", authenticationRequest.getEmail());
            return ResponseEntity.ok(loggedUser);
        } catch (Exception e) {
            log.info("User with email {} can't logged in", authenticationRequest.getEmail());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponseDto> registerAdmin(@RequestBody CreateAdminRequestDto createAdminRequest) {
        try {
            AuthenticationResponseDto newAdmin = authenticationService.registerAdmin(createAdminRequest);
            log.info("admin with username {} is created", createAdminRequest.getUsername());
            return ResponseEntity.ok(newAdmin);
        } catch (Exception e) {
            log.error("Error while creating new admin");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refresh(@RequestParam("token") String refreshToken) {
        try {
            AuthenticationResponseDto newToken = authenticationService.refreshToken(refreshToken);
            log.info("New refresh token was generated");
            return ResponseEntity.ok(newToken);
        } catch (Exception e) {
            log.error("Error while generating refresh token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        try {
            Boolean res = authenticationService.validateToken(token);
            log.info("Token is valid");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("Token is not valid");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
