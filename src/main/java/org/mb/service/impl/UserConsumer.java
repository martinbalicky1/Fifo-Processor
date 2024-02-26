package org.mb.service.impl;

import org.mb.service.Command;

import java.util.concurrent.BlockingQueue;

public class UserConsumer implements Runnable {
    private final BlockingQueue<Command> queue;

    public UserConsumer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(3000);
                var command = queue.take();
                if (command instanceof PoisonPillCommand) {
                    System.out.println("Consumer received poison pill and is terminating.");
                    break;
                }
                command.execute();
                System.out.println(Thread.currentThread().getName() + " consumer | " + command + " ===> successfully executed");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
