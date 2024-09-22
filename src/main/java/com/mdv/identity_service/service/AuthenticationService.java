package com.mdv.identity_service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdv.identity_service.dto.request.AuthenticationRequest;
import com.mdv.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;

    public boolean authenticated(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not exist"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
