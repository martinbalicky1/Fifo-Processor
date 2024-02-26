package org.mb.service;

@FunctionalInterface
public interface UserOperations {
    void perform(UserService userService) throws InterruptedException;
}
