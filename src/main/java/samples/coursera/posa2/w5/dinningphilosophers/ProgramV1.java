package samples.coursera.posa2.w5.dinningphilosophers;

public class ProgramV1 {

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
		private static final Object monitorLock = new Object();

		private final Object conditionVar = new Object();

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
			synchronized (monitorLock) {
				this.dinningTable = dinningTable;
			}
		}

		public Philosopher getLeftPhilosopher() {
			return dinningTable.getLeftPhilosopher(philosopherNumber);
		}

		public Philosopher getRightPhilosopher() {
			return dinningTable.getRightPhilosopher(philosopherNumber);
		}

		public Object getConditionVar() {
			return conditionVar;
		}

		private void pickUpChopStick() throws InterruptedException {
			synchronized (monitorLock) {
				state = State.HUNGRY;
				if (getLeftPhilosopher().getState() != State.EATING && getRightPhilosopher().getState() != State.EATING) {
					state = State.EATING;
				} else {					
					conditionVar.wait();					
				}
			}
		}

		private synchronized void putDownChopStick() {
			synchronized (monitorLock) {
				state = State.THINKING;
				if (getLeftPhilosopher().getState() == State.HUNGRY && getLeftPhilosopher().getLeftPhilosopher().getState() != State.EATING) {
					synchronized (getLeftPhilosopher().getConditionVar()) {
						getLeftPhilosopher().getConditionVar().notify();
					}
				}

				if (getRightPhilosopher().getState() == State.HUNGRY && getRightPhilosopher().getRightPhilosopher().getState() != State.EATING) {
					synchronized (getRightPhilosopher().getConditionVar()) {
						getRightPhilosopher().getConditionVar().notify();
					}
				}
			}
		}

		private void think() {
			System.out.println("Philosopher-" + philosopherNumber + " is thinking......");
			try {
				Thread.sleep(400);
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

		public synchronized void thinkAndEat() throws InterruptedException {
			for (int i = 0; i < 5; i++) {
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
