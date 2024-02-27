package org.mb.service.impl;

import org.mb.service.Command;
import org.mb.service.UserService;

public class PrintAllCommand implements Command {
    private UserService userService;

    public PrintAllCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        userService.printAllUsers();
    }

    @Override
    public String toString() {
        return "PrintAllCommand: Printing all users";
    }
}
