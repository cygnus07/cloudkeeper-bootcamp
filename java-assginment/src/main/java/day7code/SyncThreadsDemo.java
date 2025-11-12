package day7code;

public class SyncThreadsDemo {

    public static void main(String[] args) {
        System.out.println("synchronized threads printing tables\n");

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("thread 1 2 x " + i + " = " + (2 * i));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("thread 2 4 x " + i + " = " + (4 * i));
            }
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nboth threads completed");
    }
}
