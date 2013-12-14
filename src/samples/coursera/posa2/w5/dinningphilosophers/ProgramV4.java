package samples.coursera.posa2.w5.dinningphilosophers;


/**
 * 
 * This program implements deadlock free solution : "odd philosophers pick first left then right, while even philosophers pick first right then left"
 * Ref: http://www.cs.colorado.edu/~rhan/CSCI_3753_Spring_2005/CSCI_3753_Spring_2005/Lectures/02_22_05_dp_mon_cv.pdf
 *
 */
public class ProgramV4 {

	enum Location {left, right};
	
	/**
	 * Operations on chopstick, pickup and putdown are part of a monitor 
	 *
	 */
	static class ChopstickMonitor {
		enum State {
			AVAILABLE, BUSY
		};
		
		//initially chopstick is in available to use
		private State state = State.AVAILABLE;
			
		/*parameters are only for logging purpose*/
		public synchronized void pickUp(int philosopherId, Location chopstickLocation) {
			//wait until chopstick is available
			while (state != State.AVAILABLE) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			state = State.BUSY;
			
			//logging is also made as part of critical section/monitor code, 
			//otherwise the output might looks inconsistent because of race conditions
			System.out.println("Philosopher " + philosopherId + " picks up " + chopstickLocation + " chopstick.");			
		}

		/*parameters are only for logging purpose*/
		public synchronized void putDown(int philosopherId, Location chopstickLocation) {
			state = State.AVAILABLE;

			//logging is also made as part of critical section/monitor code, 
			//otherwise the output might looks inconsistent because of race conditions
			System.out.println("Philosopher " + philosopherId + " puts down " + chopstickLocation + " chopstick.");

			//wakeup waiters for the chopstick
			notify();
		}

	}

	/**
	 * To avoid deadlock, Philosophers follow below mentioned chopstick picking strategy :
	 *                            Even philosophers pick first right then left,  
	 *                             Odd philosophers pick first left then right
	 * 
	 */
	static class Philosopher implements Runnable {
		private static final int NUMBER_OF_TIMES_TO_EAT = 5;
		private final int philosopherId;

		private final ChopstickMonitor leftChopstick;
		private final ChopstickMonitor rightChopstick;

		Philosopher(int i, ChopstickMonitor leftChopstick, ChopstickMonitor rightChopstick) {
			this.philosopherId = i;
			this.leftChopstick = leftChopstick;
			this.rightChopstick = rightChopstick;
		}

		@Override
		public void run() {
			
			for (int i = 0; i < NUMBER_OF_TIMES_TO_EAT; i++) {
				
				think();

				if (isEvenPhilosopher()) {
					// pick first right chopstick then left chopstick
					rightChopstick.pickUp(philosopherId, Location.right);					
					leftChopstick.pickUp(philosopherId, Location.left);
				} else {
					// pick first left chopstick then right chopstick
					leftChopstick.pickUp(philosopherId, Location.left);										
					rightChopstick.pickUp(philosopherId, Location.right);
				}

				eat();

				if (isEvenPhilosopher()) {
					// put down in reverse order of pick up (first left chopstick then right chopstick)					
					leftChopstick.putDown(philosopherId, Location.left);
					rightChopstick.putDown(philosopherId, Location.right);					
				} else {
					// put down in reverse order of pick up (first right chopstick then left chopstick)					
					rightChopstick.putDown(philosopherId, Location.right);
					leftChopstick.putDown(philosopherId, Location.left);
				}
			}
		}

		private void think() {
			//System.out.println("Philosopher " + philosopherId + " thinks.");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
		}

		private void eat() {
			System.out.println("Philosopher " + philosopherId + " eats.");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
		}

		private boolean isEvenPhilosopher() {
			return (philosopherId % 2 == 0);
		}
	}

	static class DinningPhilosophers {
		private static final int NUMBER_OF_PHILOSOPHERS = 5;

		private final ChopstickMonitor[] chopsticks = new ChopstickMonitor[NUMBER_OF_PHILOSOPHERS];
		private final Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];

		public DinningPhilosophers() {
			
			//create all required chopstick objects
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				chopsticks[i] = new ChopstickMonitor();
			}

			//create all required philosopher objects
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				ChopstickMonitor leftChopstick = chopsticks[(i + 1) % NUMBER_OF_PHILOSOPHERS];
				ChopstickMonitor rightChopstick = chopsticks[i];
				philosophers[i] = new Philosopher(i, leftChopstick, rightChopstick);
			}
		}

		public void start() {
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

	public static void main(String[] args) {
		DinningPhilosophers dp = new DinningPhilosophers();
		dp.start();
	}

}
