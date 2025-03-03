package com.mdv.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdv.identity_service.dto.request.UserCreateRequest;
import com.mdv.identity_service.dto.request.UserUpdateRequest;
import com.mdv.identity_service.dto.response.UserResponse;
import com.mdv.identity_service.entity.User;
import com.mdv.identity_service.exception.ApiErrorCode;
import com.mdv.identity_service.exception.ApiException;
import com.mdv.identity_service.mapper.UserMapper;
import com.mdv.identity_service.repository.RoleRepository;
import com.mdv.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND = "User not found: ";

    public UserResponse createUser(UserCreateRequest request) {
        User user = userMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(ApiErrorCode.USER_EXISTED);
        }

        return userMapper.mapToUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserResponse)
                .toList();
    }

    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND + userId));
        return userMapper.mapToUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND + name));
        return userMapper.mapToUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND + userId));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
