package sutansums.generator;

import java.util.Random;

import sutansums.problem.Addition;

public class AdditionGenerator implements IGenerator<Addition> {
	private final int numberOfOperands;
	private final int numberOfDigits;
	private final boolean exactly;
	private final Random random;

	public AdditionGenerator(int numberOfOperands, int numberOfDigits, boolean exactly) {
		this.numberOfOperands = numberOfOperands;
		this.numberOfDigits = numberOfDigits;
		this.exactly = exactly;
		this.random = new Random();
	}

	public Addition getNext() {
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
		return new Addition(operandList);
	}

	public int getNumberOfOperands() {		
		return this.numberOfOperands;
	}

	public char getSymbol() {		
		return '+';
	}

	public int getNumberOfDigits() {		
		return this.numberOfDigits;
	}

}
