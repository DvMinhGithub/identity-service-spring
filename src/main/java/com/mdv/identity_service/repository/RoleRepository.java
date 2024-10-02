package com.mdv.identity_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.mdv.identity_service.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findByName(String name);

    void deleteById(@NonNull String name);
}
