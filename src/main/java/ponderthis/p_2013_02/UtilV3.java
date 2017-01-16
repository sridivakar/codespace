package ponderthis.p_2013_02;

public class UtilV3 {

	private static class Pair {
		private long x;
		private long y;

		Pair(long x, long y) {
			this.x = x;
			this.y = y;
		}

	}

	/*
	 * Valid Last digits of a perfect square 1 x 1 = 1 2 x 2 = 4 3 x 3 = 9 4 x 4
	 * = 6 5 x 5 = 5 6 x 6 = 6 7 x 7 = 9 8 x 8 = 4 9 x 9 = 1 10 ^2 = 0
	 * 
	 * 0,1,4,5,6,9
	 */
	public static boolean isPerfectSquare(long n) {
		long lastDigit = n % 10;

		if (lastDigit != 0 && lastDigit != 1 && lastDigit != 4
				&& lastDigit != 5 && lastDigit != 6 && lastDigit != 9) {
			return false;
		}

		double squareRoot = Math.sqrt(n);

		long squareRootFloor = (long) Math.floor(squareRoot);
		long squareRootCeil = (long) Math.ceil(squareRoot);

		for (long i = squareRootFloor; i <= squareRootCeil; i++) {
			if (i * i == n) {
				return true;
			}
		}

		return false;
	}

	public static Pair isJointSquare(long n) {

		if (!isPerfectSquare(n)) {
			return null;
		}

		long i = 10;

		long firstPart;
		long secondPart;

		while (i < n) {
			firstPart = n / i;
			secondPart = n % i;
			if (isPerfectSquare(firstPart) && isPerfectSquare(secondPart)) {
				return new Pair(firstPart, secondPart);
			}
			i = i * 10;
		}

		return null;
	}

	private static long pow(long m, long n) {
		long p = 1;

		for (long i = 0; i < n; i++) {
			p *= m;
		}

		return p;
	}

	public static final long REQ_SUB_NUMBER = 9999;
	public static final long REQ_SUB_NUMBER_SIZE = 4;

	public static boolean findSolution(long size) {
		for (long suffixSize = 0; suffixSize <= (size - REQ_SUB_NUMBER_SIZE); suffixSize++) {
			long prefixSize = size - REQ_SUB_NUMBER_SIZE - suffixSize;

			for (long i = getMinForPrefix(prefixSize); i <= getMaxForPrefix(prefixSize); i++) {
				if (prefixSize != 0 && i == 0) {
					continue;
				}
				for (long j = 0; j <= getMaxForSuffix(suffixSize); j++) {
					long n;
					if (prefixSize == 0) {
						n = (long) REQ_SUB_NUMBER * pow(10, suffixSize) + j;
					} else if (suffixSize == 0) {
						n = (long) i * pow(10, 4) + REQ_SUB_NUMBER;
					} else {
						n = (long) i * pow(10, 4 + suffixSize) + REQ_SUB_NUMBER
								* pow(10, suffixSize) + j;
					}
					Pair jointSquarePair = isJointSquare(n);
					if (jointSquarePair != null && jointSquarePair.y != 0) {
						System.out.print("***********************************");
						System.out.println("n : " + n + ", prefixSize:"
								+ prefixSize + ", suffixSize: " + suffixSize
								+ ", x^2 = " + jointSquarePair.x + " y^2 ="
								+ jointSquarePair.y);

					}
				}
			}

		}
		return false;
	}



	public static boolean findSolution2(long size) {
		for (long prefixSize = 0; prefixSize <= (size - REQ_SUB_NUMBER_SIZE); prefixSize++) {
			long suffixSize = size - REQ_SUB_NUMBER_SIZE - prefixSize;

			for (long i = getMinForPrefix(prefixSize); i <= getMaxForPrefix(prefixSize); i++) {
				
				for (long j = 0; j <= getMaxForSuffix(suffixSize); j++) {
					long n;
					
					if (prefixSize == 0) {
						n = (long) REQ_SUB_NUMBER * pow(10, suffixSize) + j;
					} else if (suffixSize == 0) {
						n = (long) i * pow(10, REQ_SUB_NUMBER_SIZE) + REQ_SUB_NUMBER;
					} else {
						n = (long) i * pow(10, REQ_SUB_NUMBER_SIZE + suffixSize) + REQ_SUB_NUMBER * pow(10, suffixSize) + j;
					}
					Pair jointSquarePair = isJointSquare(n);
					//System.out.println("n : " + n + ", prefixSize:"  + prefixSize + ", suffixSize: " + suffixSize);
					if (jointSquarePair != null && jointSquarePair.y != 0) {
						System.out.print("***********************************");
						System.out.println("n : " + n + ", prefixSize:"  + prefixSize + ", suffixSize: " + suffixSize + ", x^2 = " + jointSquarePair.x + " y^2 =" + jointSquarePair.y);

					}
				}
			}

		}
		return false;
	}
	
	private static long getMinForPrefix(long prefixSize) {
		if (prefixSize == 0) {
			return 0;
		}
		
		long r = 1;

		for (long i = 1; i < prefixSize; i++) {
			r = r * 10;
		}

		return r;
	}

	private static long getMaxForPrefix(long prefixSize) {
		if (prefixSize == 0) {
			return 0;
		}
		
		long p = 9;

		for (long i = 1; i < prefixSize; i++) {
			p = p * 10 + 9;
		}

		return p;
	}

	private static long getMaxForSuffix(long suffixSize) {
		if (suffixSize == 0) {
			return 0;
		}
		
		
		long p = 9;

		for (long i = 1; i < suffixSize; i++) {
			p = p * 10 + 9;
		}

		return p;
	}

	public static void main(String[] args) {
		System.out.println("isPerfectSquare(16) : " + isPerfectSquare(16));
		System.out.println("isPerfectSquare(9) : " + isPerfectSquare(9));
		System.out.println("isPerfectSquare(225) : " + isPerfectSquare(225));
		System.out.println("isPerfectSquare(164) : " + isPerfectSquare(164));

		System.out.println("isJointSquare(169) : " + isJointSquare(169));
		System.out.println("isJointSquare(225) : " + isJointSquare(225));
		System.out.println("isJointSquare(166) : " + isJointSquare(166));

		for (int i = 6; i < 19; i++) {
			findSolution2(i);
		}
		
		System.out.println(Long.MAX_VALUE);
	}

}
