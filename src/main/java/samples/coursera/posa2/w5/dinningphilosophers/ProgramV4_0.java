package samples.coursera.posa2.w5.dinningphilosophers;

public class ProgramV4_0 {

	static class ChopstickMonitor {
		enum State {
			AVAILABLE, BUSY
		};

		private State state = State.AVAILABLE;

		public synchronized void pickUpChopstick() {
			while (state != State.AVAILABLE) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			state = State.BUSY;
		}

		public synchronized void putDownChopstick() {
			state = State.AVAILABLE;

			notify();
		}

	}

	/**
	 * even philosophers pick first right then left odd philosophers pick first
	 * left then right
	 * 
	 */
	static class Philosopher implements Runnable {
		private static final int NUMBER_OF_TIMES_TO_EAT = 5;
		private final int id;

		private final ChopstickMonitor leftChopstick;
		private final ChopstickMonitor rightChopstick;

		Philosopher(int i, ChopstickMonitor leftChopstick, ChopstickMonitor rightChopstick) {
			this.id = i;
			this.leftChopstick = leftChopstick;
			this.rightChopstick = rightChopstick;
		}

		@Override
		public void run() {
			for (int i = 0; i < NUMBER_OF_TIMES_TO_EAT; i++) {
				think();

				if (isEvenPhilosopher()) {
					// pick first right chopstick then left chopstick
					rightChopstick.pickUpChopstick();
					System.out.println("Philosopher " + id + " picks up right chopstick.");
					
					leftChopstick.pickUpChopstick();
					System.out.println("Philosopher " + id + " picks up left chopstick.");
				} else {
					// pick first left chopstick then right chopstick
					leftChopstick.pickUpChopstick();
					System.out.println("Philosopher " + id + " picks up left chopstick.");
					
					rightChopstick.pickUpChopstick();
					System.out.println("Philosopher " + id + " picks up right chopstick.");
				}

				eat();

				if (isEvenPhilosopher()) {
					// put down in reverse order of pick up (first left chopstick then right chopstick)
					
					leftChopstick.putDownChopstick();
					System.out.println("Philosopher " + id + " puts down left chopstick.");
					
					rightChopstick.putDownChopstick();
					System.out.println("Philosopher " + id + " puts down right chopstick.");
				} else {
					// put down in reverse order of pick up (first right chopstick then left chopstick)
					
					rightChopstick.putDownChopstick();
					System.out.println("Philosopher " + id + " puts down right chopstick.");
					
					leftChopstick.putDownChopstick();
					System.out.println("Philosopher " + id + " puts down left chopstick.");
				}

			}
		}

		private void think() {
			//System.out.println("Philosopher " + id + " thinks.");
		}

		private void eat() {
			System.out.println("Philosopher " + id + " eats.");
		}

		private boolean isEvenPhilosopher() {
			return (id % 2 == 0);
		}
	}

	static class DP {
		private static final int NUMBER_OF_PHILOSOPHERS = 2;

		private final ChopstickMonitor[] chopsticks = new ChopstickMonitor[NUMBER_OF_PHILOSOPHERS];
		private final Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];

		public DP() {
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				chopsticks[i] = new ChopstickMonitor();
			}

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

			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				philosopherThreads[i].start();
			}
			
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
		DP dp = new DP();
		dp.start();
	}

}
