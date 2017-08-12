package com.tu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tu.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
