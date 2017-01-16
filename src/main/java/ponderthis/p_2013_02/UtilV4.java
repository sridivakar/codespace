package ponderthis.p_2013_02;

import java.util.HashSet;
import java.util.Set;

public class UtilV4 {

	public static final long[] pow10n = new long [20];
	public static final long[] maxForSuffixN = new long [20];
	public static final long[] maxForPrefixN = new long [20];
	public static final long[] minForPrefixN = new long [20];
	static {
	
		long p = 1;
		for (int i = 0; i < 20; i++) {
			pow10n[i] = p;
			p *= 10;
			
		}
	
		maxForSuffixN[0] = 0;
		maxForPrefixN[0] = 0;
		p = 9;
		for (int i = 1; i < 20; i++) {
			maxForSuffixN[i] = p;
			maxForPrefixN[i] = p;
			p = p * 10 + 9;			
		}
		
		
		minForPrefixN[0] = 0;
		p = 1;
		for (int i = 1; i < 20; i++) {			
			minForPrefixN[i] = p;
			p = p * 10;			
		}
	}
	
	public static class Pair {
		public long x;
		public long y;

		Pair(long x, long y) {
			this.x = x;
			this.y = y;
		}

	}

	/*
	 * Valid Last digits of a perfect square 

	 * 1^2  = 1 
	 * 2^2  = 4 
	 * 3^2  = 9 
	 * 4^2  = 6 
	 * 5^2  = 5 
	 * 6^2  = 6 
	 * 7^2  = 9 
	 * 8^2  = 4 
	 * 9^2  = 1 
	 * 10^2 = 0
	 * 
	 * 0,1,4,5,6,9
	 */
	public static boolean isProbablePerfectSquare(long n) {
		int lastDigit = (int) n % 10;

		switch (lastDigit) {
		case 0:
		case 1:
		case 4:
		case 5:
		case 6:
		case 9:
			return true;
		default :
			return false;
		}		
	}

	public static boolean isProbablePerfectSquare2(long n) {
		int lastDigit = (int) n % 10;

		switch (lastDigit) {
		case 0:
		case 1:
		case 4:
		case 5:
		case 6:
		case 9:
			break;
		default :
			return false;
		}		
		
		if (n < 10) {
			return true;
		}
		
		int last2Digits = (int) n % 100;
		switch(last2Digits) {
		 case 0:
		 case 1:
		 case 4: 
		 case 9:
		 case 16:
		 case 21:
		 case 24:
		 case 25:
		 case 29:
		 case 36:
		 case 41:
		 case 44:
		 case 49:
		 case 56:
		 case 61:
		 case 64:
		 case 69:
		 case 76:
		 case 81:
		 case 84:
		 case 89:
		 case 96:
			 return true;
		 default :
			 return false;
		}
		
	}
	
	public static boolean isPerfectSquare(long n) {
		if (!isProbablePerfectSquare(n)) {
			return false;
		}

		double squareRoot = Math.sqrt(n);

		long longSquareRoot = (long) squareRoot;
		if (longSquareRoot * longSquareRoot == n) {
			return true;
		}
		/*long squareRootFloor = (long) Math.floor(squareRoot);
		long squareRootCeil = (long) Math.ceil(squareRoot);

		for (long i = squareRootFloor; i <= squareRootCeil; i++) {
			if (i * i == n) {
				return true;
			}
		}*/

		return false;
	}
	
	public static boolean isPerfectSquare2(long n) {
		long lastDigit = n % 10;

		if (lastDigit != 0 && lastDigit != 1 && lastDigit != 4 && lastDigit != 5 && lastDigit != 6 && lastDigit != 9) {
			return false;
		}

		long high = n/2;
		long low = 0;
		
		while (low < high) {
			long mid = (low + high )/ 2;
			long square = mid * mid;

			if (square == n) {
				return true;
			}
			
			if (square < n) {
				low  = mid + 1;
			} else {
				high = mid - 1;
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
	
	public static long pow(long m, long n) {
		if (n == 0) {
			return 1;
		}
		
		if (n == 1) {
			return m;
		}
		
		if (n == 2) {
			return m * m;
		}
		
		long mid = n/2;
		
		if (n - mid == mid) {
			long term = pow(m, mid);
			return term * term;
		} else {
			long term = pow(m, mid);
			return term * term * m;
		}		
	}
	
	
	public static long pow10(long n) {
		return pow10n[(int)n];
	}
	
	public static long getMinForPrefix(long prefixSize) {
		if (prefixSize == 0) {
			return 0;
		}
		
		long r = 1;

		for (long i = 1; i < prefixSize; i++) {
			r = r * 10;
		}

		return r;
	}

	public static long getMaxForPrefix(long prefixSize) {
		if (prefixSize == 0) {
			return 0;
		}
		
		long p = 9;

		for (long i = 1; i < prefixSize; i++) {
			p = p * 10 + 9;
		}

		return p;
	}

	public static long getMaxForSuffix(long suffixSize) {
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
		Set<Long> set = new HashSet<Long>();
		for(long i = 0; i <  100000; i++) {
			long square = (i * i);
			set.add(square % 1000);
			//set.add(square % 100);
		}
		System.out.println(set);
	}

}
