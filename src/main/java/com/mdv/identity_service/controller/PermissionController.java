package com.mdv.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdv.identity_service.dto.request.PermissionRequest;
import com.mdv.identity_service.dto.response.ApiRespone;
import com.mdv.identity_service.dto.response.PermissionResponse;
import com.mdv.identity_service.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiRespone<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiRespone.<PermissionResponse>builder()
                .code(201)
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiRespone<List<PermissionResponse>> getAll() {
        return ApiRespone.<List<PermissionResponse>>builder()
                .code(200)
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiRespone<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiRespone.<Void>builder()
                .code(200)
                .message("Deleted permission")
                .build();
    }
}
