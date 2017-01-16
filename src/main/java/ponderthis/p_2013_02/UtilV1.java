package ponderthis.p_2013_02;

public class UtilV1 {

	/* Valid Last digits of a perfect square
	 * 1 x 1 = 1
	 * 2 x 2 = 4
	 * 3 x 3 = 9
	 * 4 x 4 = 6
	 * 5 x 5 = 5
	 * 6 x 6 = 6
	 * 7 x 7 = 9
	 * 8 x 8 = 4
	 * 9 x 9 = 1
	 * 10 ^2 = 0
	 * 
	 * 0,1,4,5,6,9
	 */	
	public static boolean isPerfectSquare(long n) {
		long lastDigit = n % 10;
		
		if (lastDigit != 0 &&  
			lastDigit != 1 &&
			lastDigit != 4 &&
			lastDigit != 5 &&
			lastDigit != 6 &&
			lastDigit != 9) {
			return false;
		}
		
		double squareRoot = Math.sqrt(n);
		
		long squareRootFloor = (long) Math.floor(squareRoot) ;
		long squareRootCeil = (long)  Math.ceil(squareRoot) ;
		
		for (long i = squareRootFloor; i <= squareRootCeil; i++){
			if (i * i == n) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isJointSquare(long n){
	
		if (!isPerfectSquare(n)) {
			return false;
		}
		
		long i = 10;
		
		long firstPart;
		long secondPart;
		
		while (i < n) {
			firstPart = n / i;
			secondPart = n % i;
			if (isPerfectSquare(firstPart) && isPerfectSquare(secondPart)) {
				return true;
			}
			i = i * 10;
		}
				
		return false;
	}

	public static final int REQ_SUB_NUMBER = 9999;
	public static final int REQ_SUB_NUMBER_SIZE = 4;
	
	public static boolean findSolution(int size) {
		
		for (int suffixSize = 0; suffixSize <= (size - REQ_SUB_NUMBER_SIZE); suffixSize++) {
			int prefixSize = size - 4 - suffixSize;
			
			for (int i = getMinWith(prefixSize); i <= getMaxWith(prefixSize); i++) {
				if ( prefixSize != 0 &&  i == 0 ) {
					continue;
				}
				for (int j = 0; j <= getMaxWith(suffixSize); j++) {
					long n;
					if (prefixSize == 0) {
						n = (long) REQ_SUB_NUMBER * pow(10, suffixSize) + j;
					} else if (suffixSize == 0) {
						n = (long)i * pow(10, 4) + REQ_SUB_NUMBER;
					} else {					
						n = (long)i * pow(10, 4 + suffixSize) +
								REQ_SUB_NUMBER * pow(10, suffixSize) +
								j;
					}
					boolean isJoint = isJointSquare(n);					
					if (isJoint) {
						System.out.print("***********************************");
						System.out.println("n : " + n + ", prefixSize:" + prefixSize +", suffixSize: " + suffixSize + ", isJointSquare: " + isJoint);
						
					}
				}
			}

		}
		return false;
	}
	
	private static int pow(int m, int n) {
		int p = 1;
		
		for (int i = 0; i< n; i++){
			p *= m;
		}

		return p;
	}

	private static int getMinWith(int suffixSize) {
		if (suffixSize == 1 || suffixSize == 0) {
			return 0;
		}
		
		int r = 1;
		
		for (int i = 1; i < suffixSize; i++){
			r = r * 10;
		}
		
		return r;
	}

	private static int getMaxWith(int suffixSize) {
		if (suffixSize == 0) {
			return 0;
		}
		
		int p = 9;
		
		for (int i = 1; i < suffixSize; i++) {
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
		
		findSolution(10);
	}

}
