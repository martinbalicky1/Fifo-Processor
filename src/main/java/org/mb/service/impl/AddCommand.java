package org.mb.service.impl;

import org.mb.entity.User;
import org.mb.service.Command;
import org.mb.service.UserService;

public class AddCommand implements Command {
    private UserService userService;
    private User user;

    public AddCommand(UserService userService, User user) {
        this.userService = userService;
        this.user = user;
    }

    @Override
    public void execute() {
        userService.addUser(user);
    }

    @Override
    public String toString() {
        return "AddUserCommand: Adding user with name " + user.userName();
    }
}
