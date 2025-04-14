import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

    private final int id;
    // Using ReentrantLock allows for tryLock and  fairness settings
    private final Lock lock;

    public Chopstick(int id) {
        this.id = id;
        this.lock = new ReentrantLock(true);
    }

    // Attempt to pick up the chopstick (otherwise blocks until available)
    public void pickUp(Philosopher philosopher, String side) throws InterruptedException {

        lock.lock(); // Blocks until the lock is free

        System.out.println(philosopher + " picked up " + side + " " + this);

    }

    // Put down the chopstick
    public void putDown(Philosopher philosopher, String side) {
        lock.unlock();
        System.out.println(philosopher + " put down " + side + " " + this);
    }

    @Override
    public String toString() {
        return "Chopstick-" + id;
    }
}