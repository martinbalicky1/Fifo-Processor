package org.mb.service.impl;

import org.mb.service.Command;
import org.mb.service.UserOperations;
import org.mb.service.UserService;

import java.util.concurrent.BlockingQueue;

public class UserProducer implements Runnable {
    private final BlockingQueue<Command> queue;
    private final UserService userService;
    private final UserOperations userOperations;

    public UserProducer(BlockingQueue<Command> queue, UserService userService, UserOperations userOperations) {
        this.queue = queue;
        this.userService = userService;
        this.userOperations = userOperations;
    }

    @Override
    public void run() {
        try {
            userOperations.perform(userService);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer was interrupted during sleep");
        }
    }
}
