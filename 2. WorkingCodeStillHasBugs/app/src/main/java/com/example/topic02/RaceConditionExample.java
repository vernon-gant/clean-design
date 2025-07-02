package com.example.topic02;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionExample {

    private static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        int numberOfThreads = 10;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    counter.incrementAndGet();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final counter value: " + counter);
    }
}

// In the wrong example the problem was with the fact that the increment operation is not atomic by its nature. It consists of 3 operations : read the value, increase the value and write the value.
// When multiple threads try to do this it may come to a situation when two threads read 10 and increased the value to 11, however one thread will write the value before another one and the seond
// one will just overwrite it and the result will be 11 and not 12 as anticipated. To fix this we could use an atomic integer implementation which is a primitive for atomic work with int.