package samples.coursera.posa2.w4.pingpong;


/**
 * Java program to create two threads, PingThread and PongThread, to alternately display Ping and Pong respectively on the console.
 * The program creates output that looks like this:
 * 
 * Ready Set Go!
 * Ping!
 * Pong!
 * Ping!
 * Pong!
 * Ping!
 * Pong!
 * Done!
 *
 */
public class Program {
	
	/**
	 * Class which maintains state representing which thread's turn.
	 */
	static class Gate {
		public static enum OPEN_FOR {
			PING, PONG
		};

		private OPEN_FOR state;

		public Gate(OPEN_FOR state) {
			this.state = state;
		}

		public synchronized OPEN_FOR getState() {
			return state;
		}

		public synchronized void setState(OPEN_FOR state) {
			this.state = state;
		}
	}
	
	
	/**
	 * Ping Thread, which waits till its turn comes, then prints "Ping!" then waits again 
	 *
	 */
	static class PingThread implements Runnable {
		private final int numberOfTimes;
		private final Gate gate;
		private final Object pingMutex;
		private final Object pongMutex;

		public PingThread(int numberOfTimes, Gate gate, Object pingMutex, Object pongMutex) {
			this.numberOfTimes = numberOfTimes;
			this.gate = gate;
			this.pingMutex = pingMutex;
			this.pongMutex = pongMutex;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < numberOfTimes; i++) {
					//checks for its turn in a loop - to avoid notify leaks/wake-up issue
					//uses wait() to avoid spinning
					while (gate.getState() != Gate.OPEN_FOR.PING) {
						synchronized (pingMutex) {
							pingMutex.wait(100);
						}
					}

					System.out.println("Ping!");
					gate.setState(Gate.OPEN_FOR.PONG);

					synchronized (pongMutex) {
						pongMutex.notify();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Pong Thread, which waits till its turn comes, then prints "Pong!" then waits again 
	 *
	 */
	static class PongThread implements Runnable {
		private final int numberOfTimes;
		private final Gate gate;
		private final Object pingMutex;
		private final Object pongMutex;

		public PongThread(int numberOfTimes, Gate gate, Object pingMutex, Object pongMutex) {
			this.numberOfTimes = numberOfTimes;
			this.gate = gate;
			this.pingMutex = pingMutex;
			this.pongMutex = pongMutex;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < numberOfTimes; i++) {
					//checks for its turn in a loop - to avoid notify leaks/wake-up issue
					//uses wait() to avoid spinning
					while (gate.getState() != Gate.OPEN_FOR.PONG) {
						synchronized (pongMutex) {
							pongMutex.wait(100);
						}
					}

					System.out.println("Pong!");

					gate.setState(Gate.OPEN_FOR.PING);
					synchronized (pingMutex) {
						pingMutex.notify();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * Main thread of control, which creates two, Ping & Pong threads and waits till they are done with Ping-Pong 
	 */
	public static void main(String[] args) throws InterruptedException {
		Object pingMutex = new Object();
		Object pongMutex = new Object();

		//create gate object with initial gate opened for Ping thread
		Gate gate = new Gate(Gate.OPEN_FOR.PING);

		int numberOfTimes = 10;
		//Instead of two different classes PingThread, PongThread, we can have single generic class
		//Just for illustration and better readablity, it has be created as two different classes		
		Thread pingThread = new Thread(new PingThread(numberOfTimes, gate, pingMutex, pongMutex));
		Thread pongThread = new Thread(new PongThread(numberOfTimes, gate, pingMutex, pongMutex));

		System.out.println("Ready Set Go!");
		System.out.println();

		//start ping & pong threads to Ping-Pong!! 
		pingThread.start();
		pongThread.start();

		//Wait till ping and pong threads are done with their work.
		pingThread.join();
		pongThread.join();
		
		System.out.println("Done!");

	}

}
