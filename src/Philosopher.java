import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a Philosopher in the Dining Philosophers problem.
 * Each Philosopher is a Runnable that alternates between thinking and eating,
 * and uses two Chopsticks (shared resources) with lock-based synchronization.
 */
public class Philosopher implements Runnable {

    private final int id; // Unique ID
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;
    private final Random random;
    private final AtomicInteger eatCount = new AtomicInteger(0);

    // Volatile helps ensure changes to state are visible across threads,
    // Useful for status checks.
    private volatile boolean isFull = false;

    private enum State { THINKING, HUNGRY, EATING };
    private volatile State state = State.THINKING;

    /**
     * Constructs a Philosopher with specified ID and chopsticks.
     */
    public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.random = new Random();
    }

    /**
     * The main execution logic for the philosopher thread.
     * Simulates alternating between thinking and eating until the philosopher is full.
     */
    @Override
    public void run() {
        try {
            while (!isFull) {
                think();
                pickUpChopsticks(); // This is where locking happens
                eat();
                putDownChopsticks(); // Unlocking happens here
            }
            System.out.println(this + " is full and leaving the table.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
            System.out.println(this + " was interrupted.");
        }
    }

    /**
     * Simulates the philosopher thinking for a random amount of time.
     */
    private void think() throws InterruptedException {
        state = State.THINKING;
        System.out.println(this + " is thinking.");
        // Simulate thinking time
        Thread.sleep(random.nextInt(1000) + 500); // Sleep 0.5 to 1.5 seconds
        state = State.HUNGRY;
        System.out.println(this + " is hungry.");
    }

    /**
     * Simulates the philosophers attempts to pick up both chopsticks
     */
    private void pickUpChopsticks() throws InterruptedException {
        // Pick up chopstick on the RIGHT FIRST
        if (id == 4) {
            rightChopstick.pickUp(this, "right");
            leftChopstick.pickUp(this, "left");
        } else {
            leftChopstick.pickUp(this, "left");
            rightChopstick.pickUp(this, "right");
        }
    }

    /**
     * Simulates eating. Increments the eat count and sets isFull if done.
     */
    private void eat() throws InterruptedException {
        int currentMeal = eatCount.incrementAndGet();
        state = State.EATING;
        System.out.println(this + " is eating. Meal #" + currentMeal);
        // Simulate eating time (Between 0.5 to 1.5 seconds)
        Thread.sleep(random.nextInt(1000) + 500);

        // Probably need to set a condition to stop running eventually
        if (eatCount.get() >= 5) {
            isFull = true;
        }
    }

    /**
     * Releases both chopsticks
     */
    private void putDownChopsticks() {

        rightChopstick.putDown(this, "right");
        leftChopstick.putDown(this, "left");
    }

    /**
     * Returns whether the philosopher is done eating.
     */
    public boolean isFull() {
        return isFull;
    }

    /**
     * Returns the number of times this philosopher has eaten.
     */
    public int getEatCount() {
        return eatCount.get();
    }

    /**
     * Custom string representation for logging.
     */
    @Override
    public String toString() {
        return "Philosopher-" + id;
    }
}