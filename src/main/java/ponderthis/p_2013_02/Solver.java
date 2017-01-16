package ponderthis.p_2013_02;

import java.util.concurrent.Semaphore;

public class Solver {

	public static final long REQ_SUB_NUMB = 9999;
	public static final int REQ_SUB_NUMB_SIZE = 4;

	public static class Runner implements Runnable {
		private int size;
		private Semaphore permits;

		public Runner(int size, Semaphore permits) {
			this.size = size;
			this.permits = permits;
		}

		@Override
		public void run() {
			long starttime = System.currentTimeMillis();
			
			try {
				permits.acquire();
				System.out.println("Starting for size : " + size);
				
				for (int prefixSize = 0; prefixSize <= (size - REQ_SUB_NUMB_SIZE); prefixSize++) {					
					int suffixSize = size - REQ_SUB_NUMB_SIZE - prefixSize;
					for (long i = Util.minForPrefixN[prefixSize]; i <= Util.maxForPrefixN[prefixSize]; i++) {
						for (long j = 0; j <= Util.maxForSuffixN[suffixSize]; j++) {
							long n;
							
							if (prefixSize == 0) {
								n = (long) REQ_SUB_NUMB * Util.pow10n[suffixSize] + j;
							} else if (suffixSize == 0) {
								n = (long) i * Util.pow10n[(int)REQ_SUB_NUMB_SIZE] + REQ_SUB_NUMB;
							} else {
								n = (long) i * Util.pow10n[REQ_SUB_NUMB_SIZE + suffixSize] + REQ_SUB_NUMB * Util.pow10n[suffixSize] + j;
							}
							
							Util.Pair jointSquarePair = Util.isJointSquare(n);
							// System.out.println("n : " + n + ", prefixSize:" + prefixSize + ", suffixSize: " + suffixSize);
							if (jointSquarePair != null && jointSquarePair.x != 0 && jointSquarePair.y != 0) {
								System.out.print("***********************************");
								System.out.println("n : " + n + ", prefixSize:" + prefixSize + ", suffixSize: " + suffixSize + ", x^2 = " + jointSquarePair.x + " y^2 =" + jointSquarePair.y);

							}
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				long endtime = System.currentTimeMillis();
				System.out.println("\t\t\t\tDone for size : " + size + " Time taken : " + (endtime - starttime) + " millis");
				permits.release();
			}
			return;
		}
	}

	public static void main(String[] args) {

		Semaphore permits = new Semaphore(1);

		for (int i = 15; i <= 18; i++) {
			Runner runner = new Runner(i, permits);
			Thread t = new Thread(runner);
			t.start();
		}
	}

}
