package org.mb.service.impl;

import org.mb.service.Command;
import org.mb.service.UserService;

public class DeleteAllCommand implements Command {
    private UserService userService;

    public DeleteAllCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        userService.deleteAllUsers();
    }

    @Override
    public String toString() {
        return "DeleteAllCommand: Deleting all users";
    }
}
