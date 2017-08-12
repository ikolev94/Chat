package com.tu.service;

import com.tu.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
