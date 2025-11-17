package day7code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadsDemo {
    public static final Object lock = new Object();
    public static boolean thread1Turn = true;

    public static void main(String[] args) {
        System.out.println("executor service threads printing tables\n");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock){
                    while(!thread1Turn){
                        try{
                            lock.wait();
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("executor thread 1 2 x " + i + " = " + (2 * i));
                    thread1Turn = false;
                    lock.notify();
                }

            }
        };

        Runnable task2 = () -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock){
                    while(thread1Turn){
                        try{
                            lock.wait();
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("executor thread 2 4 x " + i + " = " + (4 * i));
                    thread1Turn = true;
                    lock.notify();
                }
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
