package com.mdv.identity_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.mdv.identity_service.entity.User;
import com.mdv.identity_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ApiRespone<User> createRequest(@RequestBody @Valid UserCreateRequest request) {
        User user = userService.createRequest(request);
        return new ApiRespone<>(200, null, user);
    }

    @GetMapping
    public ApiRespone<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ApiRespone<>(200, null, users);
    }

    @GetMapping("/{userId}")
    public ApiRespone<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return new ApiRespone<>(200, null, user);
    }

    @PutMapping("/{userId}")
    public ApiRespone<User> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        User user = userService.updateUser(userId, request);
        return new ApiRespone<>(200, null, user);
    }

    @DeleteMapping("/{userId}")
    public ApiRespone<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ApiRespone<>(200, null, null);
    }
}
