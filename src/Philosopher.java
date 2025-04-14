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
        try{
            while(!isFull) {
                think();
                pickUpChopsticks();
                eat();
                putDownChopsticks();
            }
            System.out.println(this + " Says: I'm full.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(this + "was interrupted");
        }
    }

    private void think() throws InterruptedException {
        state = State.THINKING;
        System.out.println(id + " Says: I'm thinking...");

        // Simulate the time spent thinking
        Thread.sleep(random.nextInt(1000) + 500); // Sleep between half a second to a second
        state = State.HUNGRY;
        System.out.println(id + " Says: I'm hungry!");
    }

    private void pickUpChopsticks() throws InterruptedException {
        //Pickup the left chopstick FIRST
        leftChopstick.pickUp(this,"left");
        //Then the right
        rightChopstick.pickUp(this,"right");
    }

    private void eat() throws InterruptedException {
        state = State.EATING;
        System.out.println(id + " Says: I'm eating. Om nom nom.");
        // Simulate the time spent eating (between half a second to a second)
        Thread.sleep(random.nextInt(1000) + 500);

        // Big boy has had 5 meals, he is full
        if (eatCount >= 5){
            isFull = true;
        }
    }

    private void putDownChopsticks() {
        // Put down the right chopstick FIRST!
        rightChopstick.putDown(this,"right");
        leftChopstick.putDown(this,"left");

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