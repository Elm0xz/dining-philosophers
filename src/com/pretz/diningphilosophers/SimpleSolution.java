package com.pretz.diningphilosophers;

public class SimpleSolution {

    public static void main(String[] args) {
        Object fork1 = new Object();
        Object fork2 = new Object();
        Object fork3 = new Object();
        Object fork4 = new Object();
        Object fork5 = new Object();

        SimpleSolutionPhilosopher phil1 = new SimpleSolutionPhilosopher(1, "Plato", fork1, fork2);
        SimpleSolutionPhilosopher phil2 = new SimpleSolutionPhilosopher(2, "Jacques Derrida", fork2, fork3);
        SimpleSolutionPhilosopher phil3 = new SimpleSolutionPhilosopher(3, "Aristotle", fork3, fork4);
        SimpleSolutionPhilosopher phil4 = new SimpleSolutionPhilosopher(4, "Immanuel Kant", fork4, fork5);
        SimpleSolutionPhilosopher phil5 = new SimpleSolutionPhilosopher(5, "Andrzej Duda", fork5, fork1);

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

class SimpleSolutionPhilosopher implements Runnable, Philosopher {
    private static int eatingTime = 10;
    private static int restingTime = 5;

    private int id;
    private String name;
    private final Object leftFork;
    private final Object rightFork;

    public SimpleSolutionPhilosopher(int philId, String name, Object leftFork, Object rightFork) {
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
            synchronized (leftFork) {
                synchronized (rightFork) {
                    eat();
                    i++;
                }
            }
            rest();
        }
        System.out.println(name + " (Philosopher " + id + ") leaving.");
    }

    public void eat() {

        System.out.println(name + " (Philosopher " + id + ") started eating.");
        try {
            Thread.sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " (Philosopher " + id + ") finished eating.");

    }

    public void rest() {

        try {
            Thread.sleep(restingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

