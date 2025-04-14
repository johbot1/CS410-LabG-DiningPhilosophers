package src;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

    private final int id;
    // Using ReentrantLock allows for tryLock and  fairness settings
    private final Lock lock;

    public Chopstick(int id, Lock lock) {
        this.id = id;
        this.lock = lock;
    }

    // Attempt to pick up the chopstick (otherwise blocks until available)
    public boolean pickUp(Philosopher philosopher, String side) throws InterruptedException {

    }

    // Put down the chopstick
    public void putDown(Philosopher philosopher, String side) {
    }

    @Override
    public String toString() {
        return "Chopstick-" + id;
    }
}
