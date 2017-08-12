package com.tu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tu.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
