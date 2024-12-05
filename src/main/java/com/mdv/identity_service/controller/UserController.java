package com.mdv.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdv.identity_service.dto.request.UserCreateRequest;
import com.mdv.identity_service.dto.request.UserUpdateRequest;
import com.mdv.identity_service.dto.response.ApiResponse;
import com.mdv.identity_service.dto.response.UserResponse;
import com.mdv.identity_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse user = userService.createUser(request);
        return new ApiResponse<>(200, null, user);
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return new ApiResponse<>(200, null, users);
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        UserResponse user = userService.getUser(userId);
        return new ApiResponse<>(200, null, user);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        UserResponse user = userService.getMyInfo();
        return new ApiResponse<>(200, null, user);
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        UserResponse user = userService.updateUser(userId, request);
        return new ApiResponse<>(200, null, user);
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ApiResponse<>(200, "Delete user successfully", null);
    }
}
