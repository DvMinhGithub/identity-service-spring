package com.mdv.identity_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mdv.identity_service.dto.request.UserCreateRequest;
import com.mdv.identity_service.dto.request.UserUpdateRequest;
import com.mdv.identity_service.dto.response.UserResponse;
import com.mdv.identity_service.entity.User;
import com.mdv.identity_service.mapper.UserMapper;
import com.mdv.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    UserMapper UserMapper;

    public User createRequest(UserCreateRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        User user = UserMapper.mapToUser(request);

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return UserMapper.mapToUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        UserMapper.updateUser(user, request);

        return UserMapper.mapToUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
