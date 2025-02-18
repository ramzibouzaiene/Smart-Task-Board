package com.smarttaskboard.app;

import com.smarttaskboard.app.model.Role;
import com.smarttaskboard.app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByNameIgnoreCase("USER").isEmpty()) {
            log.info("Initialize User Role");
            roleRepository.save(new Role(null, "USER"));
        }
        if (roleRepository.findByNameIgnoreCase("ADMIN").isEmpty()) {
            log.info("Initialize Admin Role");
            roleRepository.save(new Role(null, "ADMIN"));
        }
    }
}
