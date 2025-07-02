package com.example.topic02;

public class DeadlockExample {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1 acquired lock1");

                try { Thread.sleep(50); }
                catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (lock2) {
                    System.out.println("Thread 1 acquired lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2 acquired lock2");

                try { Thread.sleep(50); }
                catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (lock1) {
                    System.out.println("Thread 2 acquired lock1");
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");
    }
}

// The problem here happens because of the distinct order of resources locking. Thread one acquires lock1 and then waits what creates space for the thread 2 to for sure acquire the lock2.
// Then they both do not release the lock and wait for each other producing deadlocks. There are multiple solutions to this problem. 1) Make the order of acquiring locks the same to prevent
// such cases. 2) Have timeouts with modern lock objects. 3) Just restructure program and avoid multiple locks.