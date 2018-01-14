package com.pretz.diningphilosophers;

import java.util.concurrent.Semaphore;

public class SemaphoreSolution {

    public static void main(String[] args) {
        Semaphore fork1 = new Semaphore(1);
        Semaphore fork2 = new Semaphore(1);
        Semaphore fork3 = new Semaphore(1);
        Semaphore fork4 = new Semaphore(1);
        Semaphore fork5 = new Semaphore(1);

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
    private static int restingTime = 1;

    private int id;
    private String name;
    private final Semaphore leftFork;
    private final Semaphore rightFork;

    public SemaphoreSolutionPhilosopher(int philId, String name, Semaphore leftFork, Semaphore rightFork) {
        id = philId;
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        System.out.println(name + " (Philosopher " + id + ") has arrived.");
    }

    @Override
    public void run() {
        boolean leftForkAcquired;
        boolean rightForkAcquired;
        int i = 0;
        while (i < 100) {
            leftForkAcquired = leftFork.tryAcquire();
            if (leftForkAcquired) {
                rightForkAcquired = rightFork.tryAcquire();
                if (rightForkAcquired) {
                    eat();
                    i++;
                    rightFork.release();
                }
                leftFork.release();
                rest();
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
}
