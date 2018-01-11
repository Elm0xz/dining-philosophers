package com.pretz.diningphilosophers;

import java.util.concurrent.Semaphore;

import static com.pretz.diningphilosophers.SemaphoreSolution.philSemaphore;

public class SemaphoreSolution {
    protected static Semaphore philSemaphore;

    public static void main(String[] args) {
        Object fork1 = new Object();
        Object fork2 = new Object();
        Object fork3 = new Object();
        Object fork4 = new Object();
        Object fork5 = new Object();

        philSemaphore = new Semaphore(5);

        Philosopher phil1 = new SemaphoreSolutionPhilosopher(1, "St. Augustine", fork1, fork2);
        Philosopher phil2 = new SemaphoreSolutionPhilosopher(2, "Friedrich Nietzsche", fork2, fork3);
        Philosopher phil3 = new SemaphoreSolutionPhilosopher(3, "David Hume", fork3, fork4);
        Philosopher phil4 = new SemaphoreSolutionPhilosopher(4, "Epicurus", fork4, fork5);
        Philosopher phil5 = new SemaphoreSolutionPhilosopher(5, "Vladimir Putin", fork5, fork1);

        Thread thread1 = new Thread(phil1, "Philosopher 1");
        Thread thread2 = new Thread(phil2, "Philosopher 2");
        Thread thread3 = new Thread(phil3, "Philosopher 3");
        Thread thread4 = new Thread(phil4, "Philosopher 4");
        Thread thread5 = new Thread(phil5, "Philosopher 5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}

class SemaphoreSolutionPhilosopher implements Philosopher {
    private static int eatingTime = 10;
    private static int restingTime = 3;

    private int id;
    private String name;
    private final Object leftFork;
    private final Object rightFork;

    public SemaphoreSolutionPhilosopher(int philId, String name, Object leftFork, Object rightFork) {
        id = philId;
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        System.out.println(name + " (Philosopher " + id + ") has arrived.");
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 100) {
            try {
                checkAvailablePermits();
                philSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                eat();
            } finally {
                checkAvailablePermits();
                philSemaphore.release();
                i++;
            }
        }
        System.out.println(name + " (Philosopher " + id + ") leaving.");
    }

    @Override
    public void eat() {
        System.out.println(name + " (Philosopher " + id + ") started eating.");
        try {
            Thread.sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " (Philosopher " + id + ") finished eating.");
    }

    @Override
    public void rest() {
        try {
            Thread.sleep(restingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkAvailablePermits() {
        System.out.println("Available permits: " + philSemaphore.availablePermits());
    }


}
