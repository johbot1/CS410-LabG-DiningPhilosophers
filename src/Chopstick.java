import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a single chopstick used in the Dining Philosophers simulation.
 * Each chopstick is protected with a ReentrantLock to ensure mutual exclusion
 * when being picked up or put down by philosophers.
 */
public class Chopstick {

    private final int id; // Unique identifier for this chopstick
    private final Lock lock; // Lock to control access to this chopstick

    /**
     * Constructs a Chopstick with the given ID.
     * Uses a fair ReentrantLock to reduce thread starvation.
     *
     * @param id The ID of the chopstick.
     */
    public Chopstick(int id) {
        this.id = id;
        this.lock = new ReentrantLock(true); // Fair lock to ensure fair access order
    }

    /**
     * Attempts to pick up this chopstick.
     * Blocks until the lock is acquired by the current philosopher.
     *
     * @param philosopher The philosopher attempting to pick up the chopstick.
     * @param side        Indicates whether it's the left or right chopstick.
     */
    public void pickUp(Philosopher philosopher, String side) {
        lock.lock(); // Acquire lock, blocks if already held by another philosopher
        System.out.println(philosopher + " picked up " + side + " " + this);
    }

    /**
     * Releases the chopstick so others may use it.
     *
     * @param philosopher The philosopher putting down the chopstick.
     * @param side        Indicates whether it's the left or right chopstick.
     */
    public void putDown(Philosopher philosopher, String side) {
        lock.unlock(); // Release the lock
        System.out.println(philosopher + " put down " + side + " " + this);
    }

    /**
     * Provides a readable string representation of this chopstick.
     *
     * @return A string like "Chopstick-0".
     */
    @Override
    public String toString() {
        return "Chopstick-" + id;
    }
}
