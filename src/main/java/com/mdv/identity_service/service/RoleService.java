package com.mdv.identity_service.service;

import java.util.List;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.mdv.identity_service.dto.request.RoleRequest;
import com.mdv.identity_service.dto.response.RoleResponse;
import com.mdv.identity_service.entity.Permission;
import com.mdv.identity_service.entity.Role;
import com.mdv.identity_service.mapper.RoleMapper;
import com.mdv.identity_service.repository.PermissionRepository;
import com.mdv.identity_service.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.mapToRole(request);

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::mapToRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
