package com.mdv.identity_service.mapper;

import org.mapstruct.Mapper;

import com.mdv.identity_service.dto.request.PermissionRequest;
import com.mdv.identity_service.dto.response.PermissionResponse;
import com.mdv.identity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission mapToPermission(PermissionRequest request);

    PermissionResponse mapToPermissionResponse(Permission permission);
}
