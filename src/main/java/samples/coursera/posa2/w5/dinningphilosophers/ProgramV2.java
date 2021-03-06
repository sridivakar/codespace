package samples.coursera.posa2.w5.dinningphilosophers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProgramV2 {

	private static final int NUMBER_OF_PHILOSOPHERS = 5;

	enum State {
		EATING, THINKING, HUNGRY
	};

	static class DinningTable {
		private Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];

		DinningTable() {
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				philosophers[i] = new Philosopher(i);
			}
		}

		public void init() {
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				philosophers[i].setDinningTable(this);
			}

			Thread[] philosopherThreads = new Thread[NUMBER_OF_PHILOSOPHERS];

			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				philosopherThreads[i] = new Thread(philosophers[i], "Philosopher-" + i);				
			}

			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				philosopherThreads[i].start();
			}
		}

		public Philosopher getLeftPhilosopher(int i) {
			int leftId = (NUMBER_OF_PHILOSOPHERS + i - 1) % NUMBER_OF_PHILOSOPHERS;
			return philosophers[leftId];
		}

		public Philosopher getRightPhilosopher(int i) {
			int rightId = (i + 1) % NUMBER_OF_PHILOSOPHERS;
			return philosophers[rightId];
		}

	}

	static class Philosopher implements Runnable {
		private static final Lock monitorLock = new ReentrantLock();
		private static final Condition[] conditions = new Condition[NUMBER_OF_PHILOSOPHERS];

		static {
			for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
				conditions[i] = monitorLock.newCondition();
			}
			
		}
		
		private State state;
		private DinningTable dinningTable;

		private final int philosopherNumber;

		public Philosopher(int philosopherNumber) {
			this.philosopherNumber = philosopherNumber;
		}

		public State getState() {
			return state;
		}

		public void setDinningTable(DinningTable dinningTable) {			
			this.dinningTable = dinningTable;			
		}

		public Philosopher getLeftPhilosopher() {
			return dinningTable.getLeftPhilosopher(philosopherNumber);
		}

		public Philosopher getRightPhilosopher() {
			return dinningTable.getRightPhilosopher(philosopherNumber);
		}

		public Condition getConditionVar() {
			return conditions[philosopherNumber];
		}

		private void pickUpChopStick() throws InterruptedException {
			try {
				monitorLock.lock();
				state = State.HUNGRY;
				if (getLeftPhilosopher().getState() != State.EATING && getRightPhilosopher().getState() != State.EATING) {
					state = State.EATING;
				} else {					
					getConditionVar().await();					
				}
			} finally {
				monitorLock.unlock();
			}
		}

		private void putDownChopStick() {
			try {
				monitorLock.lock();
				state = State.THINKING;
				if (getLeftPhilosopher().getState() == State.HUNGRY && getLeftPhilosopher().getLeftPhilosopher().getState() != State.EATING) {
					synchronized (getLeftPhilosopher().getConditionVar()) {
						getLeftPhilosopher().getConditionVar().signal();
					}
				}

				if (getRightPhilosopher().getState() == State.HUNGRY && getRightPhilosopher().getRightPhilosopher().getState() != State.EATING) {
					synchronized (getRightPhilosopher().getConditionVar()) {
						getRightPhilosopher().getConditionVar().signal();
					}
				}
			} finally {
				monitorLock.unlock();
			}
		}

		private void think() {
			System.out.println("Philosopher-" + philosopherNumber + " is thinking......");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		private void eat() {
			System.out.println("Philosopher-" + philosopherNumber + " eats.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void thinkAndEat() throws InterruptedException {
			while (true/* end of the world */) {
				think();
				pickUpChopStick();
				eat();
				putDownChopStick();
			}

		}

		@Override
		public void run() {
			try {
				thinkAndEat();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DinningTable dinningTable = new DinningTable();

		dinningTable.init();
	}

}
