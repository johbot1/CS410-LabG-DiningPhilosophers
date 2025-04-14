# CS410-LabG-DiningPhilosophers
A solution to the classic Computer Science problem

## What is this? 
The thrid lab in our Operating systems class has us look at the famous
Dining Philosopher problem. This lab focuses heavily on synchronization,
with the goal being to simulate 5 philosophers who switch from thinking 
to eating at a shared table, with the chopsticks being the limited resource.
Each philosopher is a seperate thread, and chopsticks are protected by locks
to ensure thread-safe access. We avoided deadlock and starvation!

## Explanation of the Lab

### Technical Overview
This simulation demonstrates multithreaded synchronization by modeling each philosopher as a thread that must acquire
two shared resources (chopsticks) before proceeding to eat. The `Chopstick` class encapsulates a ReentrantLock to manage
exclusive access, ensuring that no two philosophers hold the same chopstick at once. The `Philosopher` class implements 
Runnable, cycling through thinking and eating phases. To avoid deadlock, the last philosopher is programmed to pick up 
the right chopstick first, introducing asymmetry and breaking circular wait conditions. A shared monitoring loop ensures 
the simulation ends only once all philosophers have eaten five meals.

### Concurrency:
Each philosopher runs in its own thread, continuously transitioning through states of thinking, hungry, and eating. 
Locks ensure that a chopstick cannot be held by more than one philosopher at a time. By using a fixed thread pool of 
five threads (ExecutorService), the simulation ensures simultaneous and controlled thread execution. Random delays 
simulate real-world behavior and introduce non-determinism, making concurrency behavior more realistic.
### Deadlock Avoidance:
To prevent deadlock, the program follows a well-known strategy where one philosopher (in this case, the fifth) picks up 
their chopsticks in reverse order—right first, then left. This breaks the circular wait condition, which is one of the 
four necessary conditions for deadlock. Additionally, ReentrantLock(true) is used to enable fair locking, which further 
reduces the chance of starvation or unfair resource monopolization.
### Thread Safety:
Thread safety is achieved through the use of ReentrantLock, AtomicInteger, and volatile flags. Each chopstick's lock
guarantees mutual exclusion, while the volatile and atomic constructs ensure that changes in philosopher state and meal 
count are consistently visible across threads. The simulation is gracefully shut down once all threads signal completion, 
and proper interrupt handling ensures no thread is left hanging if the program terminates early.