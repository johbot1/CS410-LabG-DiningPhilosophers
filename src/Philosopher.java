import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {

    private final int id;
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;
    private final Random random;
    private final AtomicInteger eatCount = new AtomicInteger(0);

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

    private void think() throws InterruptedException {
        state = State.THINKING;
        System.out.println(this + " is thinking.");
        // Simulate thinking time
        Thread.sleep(random.nextInt(1000) + 500); // Sleep 0.5 to 1.5 seconds
        state = State.HUNGRY;
        System.out.println(this + " is hungry.");
    }

    private void pickUpChopsticks() throws InterruptedException {
        // Deadlock prevention: philosopher-4 picks up right first
        if (id == 4) {
            rightChopstick.pickUp(this, "right");
            leftChopstick.pickUp(this, "left");
        } else {
            leftChopstick.pickUp(this, "left");
            rightChopstick.pickUp(this, "right");
        }
    }


    private void eat() throws InterruptedException {
        int currentMeal = eatCount.incrementAndGet();
        state = State.EATING;
        System.out.println(this + " is eating. Meal #" + currentMeal);
        // Simulate eating time
        Thread.sleep(random.nextInt(1000) + 500); // Sleep 0.5 to 1.5 seconds

        // Probably need to set a condition to stop running eventually
        if (eatCount.get() >= 5) {
            isFull = true;
        }
    }

    private void putDownChopsticks() {
        // Must release locks in the reverse order of acquisition *if* using nested locking
        // within a single method, but here they were acquired sequentially.
        // Crucially, always release both locks. Using try...finally in run() is essential.

        // Release order doesn't strictly matter here as they are independent locks,
        // but consistency is good. Let's release right then left.
        rightChopstick.putDown(this, "right");
        leftChopstick.putDown(this, "left");
    }


    public boolean isFull() {
        return isFull;
    }

    public int getEatCount() {
        return eatCount.get();
    }

    @Override
    public String toString() {
        return "Philosopher-" + id;
    }
}