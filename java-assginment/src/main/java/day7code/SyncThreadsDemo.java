package day7code;

public class SyncThreadsDemo {
    public static final Object lock = new Object();
    public static boolean thread1Turn = true;
    public static void main(String[] args) {
        System.out.println("synchronized threads printing tables\n");

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock){
                    while(!thread1Turn){
                        try {
                            lock.wait();
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("thread 1 2 x " + i + " = " + (2 * i));
                    thread1Turn = false;
                    lock.notify();
                }


            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock){
                    while(thread1Turn){
                        try{
                            lock.wait();
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("thread 2 4 x " + i + " = " + (4 * i));
                    thread1Turn = true;
                    lock.notify();
                }

            }
        });

        t1.start();
        t2.start();

//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        t2.start();
//
//        try {
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try{
            t1.join();
            t2.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("\nboth threads completed");
    }
}
