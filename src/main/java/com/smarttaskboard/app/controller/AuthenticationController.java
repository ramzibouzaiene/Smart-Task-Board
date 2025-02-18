package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.dto.AuthenticationRequestDto;
import com.smarttaskboard.app.dto.AuthenticationResponseDto;
import com.smarttaskboard.app.dto.CreateAdminRequestDto;
import com.smarttaskboard.app.dto.RegisterRequestDto;
import com.smarttaskboard.app.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
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
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            AuthenticationResponseDto res = authenticationService.register(registerRequest);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody AuthenticationRequestDto authenticationRequest) {
        try {
            AuthenticationResponseDto res = authenticationService.authenticate(authenticationRequest);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponseDto> registerAdmin(@RequestBody CreateAdminRequestDto createAdminRequest) {
        return ResponseEntity.ok(authenticationService.registerAdmin(createAdminRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refresh(@RequestParam("token") String refreshToken) {
        try {
            AuthenticationResponseDto res = authenticationService.refreshToken(refreshToken);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        try {
            Boolean res = authenticationService.validateToken(token);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
