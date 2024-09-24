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
import com.mdv.identity_service.dto.response.ApiRespone;
import com.mdv.identity_service.dto.response.UserResponse;
import com.mdv.identity_service.entity.User;
import com.mdv.identity_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    ApiRespone<User> createRequest(@RequestBody @Valid UserCreateRequest request) {
        User user = userService.createRequest(request);
        return new ApiRespone<>(200, null, user);
    }

    @GetMapping
    ApiRespone<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ApiRespone<>(200, null, users);
    }

    @GetMapping("/{userId}")
    ApiRespone<UserResponse> getUser(@PathVariable String userId) {
        UserResponse user = userService.getUser(userId);
        return new ApiRespone<>(200, null, user);
    }

    @PutMapping("/{userId}")
    ApiRespone<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        UserResponse user = userService.updateUser(userId, request);
        return new ApiRespone<>(200, null, user);
    }

    @DeleteMapping("/{userId}")
    ApiRespone<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ApiRespone<>(200, null, null);
    }
}
