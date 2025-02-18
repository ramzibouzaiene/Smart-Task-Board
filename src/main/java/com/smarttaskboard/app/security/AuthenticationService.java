package com.smarttaskboard.app.security;

import com.smarttaskboard.app.dto.AuthenticationRequestDto;
import com.smarttaskboard.app.dto.AuthenticationResponseDto;
import com.smarttaskboard.app.dto.CreateAdminRequestDto;
import com.smarttaskboard.app.dto.RegisterRequestDto;
import com.smarttaskboard.app.model.Role;
import com.smarttaskboard.app.model.User;
import com.smarttaskboard.app.repository.RoleRepository;
import com.smarttaskboard.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponseDto register(RegisterRequestDto registerRequest) {
        Role defaultRole = roleRepository.findByNameIgnoreCase("USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        var user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(defaultRole))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthenticationResponseDto.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto registerAdmin(CreateAdminRequestDto createAdminRequest) {
        // Ensure only admin can register other admins
        Role role = roleRepository.findByNameIgnoreCase(createAdminRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (!role.getName().equals("ADMIN")) {
            throw new IllegalArgumentException("Only ADMIN can register as ADMIN");
        }

        var user = new User();
        user.setUsername(createAdminRequest.getUsername());
        user.setEmail(createAdminRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createAdminRequest.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);

        return AuthenticationResponseDto.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthenticationResponseDto.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto refreshToken(String refreshToken) {

        var user = userRepository.findByEmail(jwtService.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var jwtToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthenticationResponseDto.builder()
                .authenticationToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
