package sutansums.generator;

import java.util.Random;

import sutansums.problem.Substraction;

public class SubstractionGenerator implements IGenerator <Substraction> {
	private final int numberOfOperands;
	private final int numberOfDigits;
	private final boolean exactly;
	private final Random random;

	public SubstractionGenerator(int numberOfDigits, boolean exactly) {
		this.numberOfOperands = 2;
		this.numberOfDigits = numberOfDigits;
		this.exactly = exactly;
		this.random = new Random();
	}

	public Substraction getNext() {
		int digitTruncator = 1;
		for (int i = 1; i <= numberOfDigits; i++) {
			digitTruncator *= 10;
		}

		Integer[] operandList = new Integer[numberOfOperands];
		for (int j = 0; j < numberOfOperands; j++) {
			int number = 0;
			do {
				number = Math.abs(random.nextInt()) % digitTruncator;
			} while (exactly && number < digitTruncator / 10);
			operandList[j] = number;
		}
		
		return new Substraction(operandList);
	}

	public int getNumberOfOperands() {		
		return this.numberOfOperands;
	}

	public char getSymbol() {		
		return '-';
	}

	public int getNumberOfDigits() {		
		return this.numberOfDigits;
	}

}
