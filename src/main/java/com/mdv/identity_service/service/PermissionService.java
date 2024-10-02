package com.mdv.identity_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mdv.identity_service.dto.request.PermissionRequest;
import com.mdv.identity_service.dto.response.PermissionResponse;
import com.mdv.identity_service.entity.Permission;
import com.mdv.identity_service.mapper.PermissionMapper;
import com.mdv.identity_service.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.mapToPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.mapToPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::mapToPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
