package com.smarttaskboard.app.service.impl;

import com.smarttaskboard.app.model.Role;
import com.smarttaskboard.app.model.User;
import com.smarttaskboard.app.repository.RoleRepository;
import com.smarttaskboard.app.repository.UserRepository;
import com.smarttaskboard.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);

    }
}
