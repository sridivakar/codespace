package samples.coursera.posa2.w5.dinningphilosophers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This program implements deadlock free solution : "allow a philosopher to pick up chopsticks only if both are free."
 * Ref: http://www.csee.wvu.edu/~jdm/classes/cs550/notes/tech/mutex/dp-mon.html
 *
 */
public class ProgramV3 {
	private static final int NUMBER_OF_PHILOSOPHERS = 5;
	private static final int NUMBER_OF_TIMES_TO_EAT = 5;

	enum State {
		EATING, THINKING, HUNGRY
	};

	static class DinningTableMonitor {
		private final Lock monitorLock = new ReentrantLock();
		private final Condition[] conditions = new Condition[NUMBER_OF_PHILOSOPHERS];

		private final State philosopherStates[] = new State[NUMBER_OF_PHILOSOPHERS];

		public DinningTableMonitor() {
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				philosopherStates[i] = State.THINKING;
			}
			
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				conditions[i] = monitorLock.newCondition();
			}
		}

		public void pickUp(int philosopherId) {
			try {
				monitorLock.lock();
				philosopherStates[philosopherId] = State.HUNGRY;
				
				int leftPhilosopherId = (philosopherId + 1) % NUMBER_OF_PHILOSOPHERS;
				int rightPhilosopherId = (philosopherId - 1 + NUMBER_OF_PHILOSOPHERS) % NUMBER_OF_PHILOSOPHERS;
				
				//wait until any of neighbors are eating
				while (philosopherStates[leftPhilosopherId] == State.EATING || philosopherStates[rightPhilosopherId] == State.EATING) {
					try {
						conditions[philosopherId].await();
					} catch (InterruptedException e) {					
						e.printStackTrace();
					}
				}
				
				//both neighbors are not eating
				System.out.println("Philosopher " + philosopherId + " picks up left chopstick.");			
				System.out.println("Philosopher " + philosopherId + " picks up right chopstick.");
				
				philosopherStates[philosopherId] = State.EATING;				
			} finally {
				monitorLock.unlock();
			}
		}

		public void putDown(int philosopherId) {
			try {
				monitorLock.lock();
				philosopherStates[philosopherId] = State.THINKING;
				
				System.out.println("Philosopher " + philosopherId + " puts down right chopstick.");			
				System.out.println("Philosopher " + philosopherId + " puts down left chopstick.");
				
				//wake up left neighbor if his left neighbor is not eating
				int leftPhilosopherId = (philosopherId + 1) % NUMBER_OF_PHILOSOPHERS;
				int leftLeftPhilosopherId = (philosopherId + 2) % NUMBER_OF_PHILOSOPHERS;
				if (philosopherStates[leftPhilosopherId] == State.HUNGRY && philosopherStates[leftLeftPhilosopherId] != State.EATING) {
					conditions[leftPhilosopherId].signal();
				}

				//wake up right neighbor if his right neighbor is not eating
				int rightPhilosopherId = (philosopherId - 1 + NUMBER_OF_PHILOSOPHERS) % NUMBER_OF_PHILOSOPHERS;
				int rightRightPhilosopherId = (philosopherId - 2 + NUMBER_OF_PHILOSOPHERS) % NUMBER_OF_PHILOSOPHERS;
				if (philosopherStates[rightPhilosopherId] == State.HUNGRY && philosopherStates[rightRightPhilosopherId] != State.EATING) {
					conditions[rightPhilosopherId].signal();
				}
			} finally {
				monitorLock.unlock();
			}
		}

	}

	/**
	 * Philosopher thread
	 *
	 */
	static class Philosopher implements Runnable {
		private final int philosopherNumber;
		private final DinningTableMonitor dinningTableMonitor;

		public Philosopher(int philosopherNumber, DinningTableMonitor dinningTableMonitor) {
			this.philosopherNumber = philosopherNumber;
			this.dinningTableMonitor = dinningTableMonitor;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < NUMBER_OF_TIMES_TO_EAT; i++) {
				think();
				dinningTableMonitor.pickUp(philosopherNumber);
				eat();
				dinningTableMonitor.putDown(philosopherNumber);
			}			
		}

		private void eat() {
			System.out.println("Philosopher " + philosopherNumber + " eats.");
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void think() {
			//System.out.println("Philosopher " + philosopherNumber + " thinks.");
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		DinningTableMonitor dinningTableMonitor = new DinningTableMonitor();
		Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
		
		//create all required philosopher objects
		for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
			philosophers[i] = new Philosopher(i, dinningTableMonitor);
		}

		Thread philosopherThreads[] = new Thread[NUMBER_OF_PHILOSOPHERS];
		for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
			philosopherThreads[i] = new Thread(philosophers[i], "Philosopher " + i);
		}

		//start all philosopher threads
		for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
			philosopherThreads[i].start();
		}
		
		//wait for all philosophers to complete
		for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
			try {
				philosopherThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Dinner is over!");
	
	}
}
