package org.mb;

import org.mb.configuration.DatabaseConnectionConfiguration;
import org.mb.entity.User;
import org.mb.repository.impl.UserRepositoryImpl;
import org.mb.service.Command;
import org.mb.service.UserOperations;
import org.mb.service.impl.*;

import java.util.concurrent.*;

import static org.mb.configuration.PropertiesLoader.loadDatabaseProperties;

public class Main {
    public static void main(String[] args) throws Exception {
        loadDatabaseProperties();
        DatabaseConnectionConfiguration.initialize();

        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        var userService = new UserServiceImpl(new UserRepositoryImpl());
        var userProducer = new UserProducer(queue, userService, getUserOperations(queue));
        var userConsumer = new UserConsumer(queue);

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(2);
            executor.submit(userProducer);
            executor.submit(userConsumer);
            executor.shutdown();
        } finally {
            if (executor != null) {
                executor.awaitTermination(1, TimeUnit.MINUTES);
                if (executor.isTerminated())
                    System.out.println("All tasks finished");
                else
                    System.out.println("At least one task is still running");
            }
        }
    }

    public static UserOperations getUserOperations(BlockingQueue<Command> queue) {
        return userService -> {
            var userRobert = new User(1, "a1", "Robert");
            queue.put(new AddCommand(userService, userRobert));
            System.out.println(Thread.currentThread().getName() + " producer | produced user with name " + userRobert.userName());
            Thread.sleep(2000);

            var userMartin = new User(2, "a2", "Martin");
            queue.put(new AddCommand(userService, userMartin));
            System.out.println(Thread.currentThread().getName() + " producer | produced user with name " + userMartin.userName());
            Thread.sleep(2000);

            queue.put(new PrintAllCommand(userService));
            Thread.sleep(2000);

            queue.put(new DeleteAllCommand(userService));
            Thread.sleep(2000);

            queue.put(new PrintAllCommand(userService));
            Thread.sleep(2000);

            queue.put(new PoisonPillCommand());
        };
    }
}