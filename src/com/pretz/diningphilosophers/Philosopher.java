package com.pretz.diningphilosophers;

public interface Philosopher extends Runnable {
    void eat();
    void rest();
}
