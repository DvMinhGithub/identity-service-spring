package com.mdv.identity_service.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.mdv.identity_service.entity.Role;
import com.mdv.identity_service.entity.User;
import com.mdv.identity_service.enums.RoleType;
import com.mdv.identity_service.repository.RoleRepository;
import com.mdv.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    private static final String USER_ROLE_NAME = RoleType.USER.name();
    private static final String ADMIN_ROLE_NAME = RoleType.ADMIN.name();

    @Bean
    @Transactional
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            roleRepository.findById(USER_ROLE_NAME).orElseGet(() -> {
                Role userRole = Role.builder().name(USER_ROLE_NAME).build();
                roleRepository.save(userRole);
                log.info("Created USER role");
                return userRole;
            });

            Role adminRole = roleRepository.findById(ADMIN_ROLE_NAME).orElseGet(() -> {
                Role role = Role.builder().name(ADMIN_ROLE_NAME).build();
                roleRepository.save(role);
                log.info("Created ADMIN role");
                return role;
            });

            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User user = User.builder()
                        .username(adminUsername)
                        .password(passwordEncoder.encode(adminPassword))
                        .roles(roles)
                        .build();

                userRepository.save(user);

                log.info("Admin user created");
            }
        };
    }
}
