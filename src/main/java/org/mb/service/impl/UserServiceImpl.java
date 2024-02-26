package org.mb.service.impl;

import org.mb.entity.User;
import org.mb.repository.UserRepository;
import org.mb.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void printAllUsers() {
        System.out.println("Users stored in DB: " + userRepository.findAll());
    }

    @Override
    public void deleteAllUsers() {
        System.out.println("Users stored in DB to be deleted: " + userRepository.findAll());
        userRepository.deleteAll();
    }
}
