package com.mdv.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mdv.identity_service.dto.request.RoleRequest;
import com.mdv.identity_service.dto.response.RoleResponse;
import com.mdv.identity_service.entity.Role;

@Mapper(componentModel = "spring")

public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role mapToRole(RoleRequest request);

    RoleResponse mapToRoleResponse(Role Role);
}
