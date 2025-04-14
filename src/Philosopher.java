package src;

import java.util.Random;

public class Philosopher implements Runnable {

    private final int id;
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;
    private final Random random;
    private int eatCount = 0;

    // Volatile helps ensure changes to state are visible across threads,
    // Useful for status checks.
    private volatile boolean isFull = false;

    // State enum for better logging (mainly for logs)
    private enum State { THINKING, HUNGRY, EATING };
    private volatile State state = State.THINKING;

    public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.random = new Random();
    }

    @Override
    public void run() {

    }

    private void think() throws InterruptedException {

    }

    private void pickUpChopsticks() throws InterruptedException {

    }

    private void eat() throws InterruptedException {

    }

    private void putDownChopsticks() {

    }


    public boolean isFull() {
        return isFull;
    }

    public int getEatCount() {
        return eatCount;
    }

    @Override
    public String toString() {
        return "Philosopher-" + id;
    }
}