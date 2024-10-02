package com.mdv.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.mdv.identity_service.entity.Permission;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    void deleteById(@NonNull String name);
}
