package org.mb.service;

import org.mb.entity.User;

public interface UserService {
    void addUser(User user);

    void printAllUsers();

    void deleteAllUsers();
}
