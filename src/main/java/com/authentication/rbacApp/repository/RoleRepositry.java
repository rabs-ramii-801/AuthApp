package com.authentication.rbacApp.repository;

import com.authentication.rbacApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositry extends JpaRepository<Role,Long> {
    Role findByroleName(String name);
}
