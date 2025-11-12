package day7code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadsDemo {

    public static void main(String[] args) {
        System.out.println("executor service threads printing tables\n");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable task1 = () -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("executor thread 1 2 x " + i + " = " + (2 * i));
            }
        };

        Runnable task2 = () -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("executor thread 2 4 x " + i + " = " + (4 * i));
            }
        };

        executor.submit(task1);
        executor.submit(task2);

        executor.shutdown();

        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("\nexecutor service completed");
    }
}
