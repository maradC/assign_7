import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 2;
    private final ReentrantLock[] forks = new ReentrantLock[NUM_PHILOSOPHERS];
    
    public DiningPhilosophers() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }
    }
    
    public void dine() {
        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];
        
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            final int philosopherId = i;
            philosophers[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 5; j++) {
                        think(philosopherId);
                        eat(philosopherId);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            philosophers[i].start();
        }
        
        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private void think(int philosopherId) throws InterruptedException {
        System.out.println("Philosopher " + philosopherId + " is thinking...");
        Thread.sleep((long) (Math.random() * 1000));
    }
    
    private void eat(int philosopherId) throws InterruptedException {
        int firstFork = Math.min(philosopherId, (philosopherId + 1) % NUM_PHILOSOPHERS);
        int secondFork = Math.max(philosopherId, (philosopherId + 1) % NUM_PHILOSOPHERS);
        
        System.out.println("Philosopher " + philosopherId + " is trying to pick up fork " + firstFork);
        forks[firstFork].lock();
        try {
            System.out.println("Philosopher " + philosopherId + " picked up fork " + firstFork);
            System.out.println("Philosopher " + philosopherId + " is trying to pick up fork " + secondFork);
            
            forks[secondFork].lock();
            try {
                System.out.println("Philosopher " + philosopherId + " picked up fork " + secondFork);
                System.out.println("Philosopher " + philosopherId + " is eating...");
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("Philosopher " + philosopherId + " has finished eating.");
            } finally {
                forks[secondFork].unlock();
                System.out.println("Philosopher " + philosopherId + " put down fork " + secondFork);
            }
        } finally {
            forks[firstFork].unlock();
            System.out.println("Philosopher " + philosopherId + " put down fork " + firstFork);
        }
    }
    
    public static void main(String[] args) {
        DiningPhilosophers table = new DiningPhilosophers();
        table.dine();
    }
}