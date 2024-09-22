package com.mdv.identity_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdv.identity_service.dto.request.AuthenticationRequest;
import com.mdv.identity_service.dto.response.ApiRespone;
import com.mdv.identity_service.dto.response.AuthenticationResponse;
import com.mdv.identity_service.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiRespone<AuthenticationResponse> postMethodName(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticated(request);

        return ApiRespone.<AuthenticationResponse>builder().code(200)
                .result(AuthenticationResponse.builder().authenticated(result).build()).build();

    }
}