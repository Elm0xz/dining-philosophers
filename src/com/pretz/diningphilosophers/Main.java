package com.pretz.diningphilosophers;

public class Main {

    public static void main(String[] args) {
        Fork fork1 = new Fork("fork1");
        Fork fork2 = new Fork("fork2");
        Fork fork3 = new Fork("fork3");
        Fork fork4 = new Fork("fork4");
        Fork fork5 = new Fork("fork5");

        Philosopher phil1 = new Philosopher(1, "Plato", fork1, fork2);
        Philosopher phil2 = new Philosopher(2, "Jacques Derrida", fork2, fork3);
        Philosopher phil3 = new Philosopher(3, "Aristotle", fork3, fork4);
        Philosopher phil4 = new Philosopher(4, "Immanuel Kant", fork4, fork5);
        Philosopher phil5 = new Philosopher(5, "Andrzej Duda", fork5, fork1);

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

class Philosopher implements Runnable {
    private static int eatingTime = 10;
    private static int restingTime = 10;
    private static boolean starting = true;

    private int id;
    private String name;
    private final Fork leftFork;
    private final Fork rightFork;

    public Philosopher(int philId, String name, Fork leftFork, Fork rightFork) {
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
/*                    if (starting) {
                        starting = false;
                    } else {
                        try {
                            leftFork.wait();
                            rightFork.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }*/
                    eat();
                    i++;
/*                    rightFork.notify();
                    leftFork.notify();*/
                }
            }
            rest();
        }
        System.out.println(name + " (Philosopher " + id + ") leaving.");
    }

    private void eat() {

        System.out.println(name + " (Philosopher " + id + ") started eating.");
        try {
            Thread.sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " (Philosopher " + id + ") finished eating.");

    }

    private void rest() {

        try {
            Thread.sleep(restingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Fork {
    private String name;

    public Fork(String name) {
        this.name = name;
    }
}

