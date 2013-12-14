package sutansums.generator;

import java.util.Random;

import sutansums.problem.Multiplication;

public class MultiplicationGenerator implements IGenerator <Multiplication> {
	private final int numberOfOperands;
	private final int multiplicandDigits;
	private final int multiplierDigits;
	private final boolean exactly;
	private final Random random;

	public MultiplicationGenerator(int multiplicandDigits, int multiplierDigits, boolean exactly) {
		this.numberOfOperands = 2;
		this.multiplicandDigits = multiplicandDigits;
		this.multiplierDigits = multiplierDigits;
		this.exactly = exactly;
		this.random = new Random();
	}

	public Multiplication getNext() {
		int multiplicandDigitTruncator = 1;
		for (int i = 1; i <= multiplicandDigits; i++) {
			multiplicandDigitTruncator *= 10;
		}
		
		int multiplierDigitTruncator = 1;
		for (int i = 1; i <= multiplierDigits; i++) {
			multiplierDigitTruncator *= 10;
		}

		Integer[] operandList = new Integer[numberOfOperands];
		int number = 0;
		do {
			number = Math.abs(random.nextInt()) % multiplicandDigitTruncator;
		} while (exactly && number < multiplicandDigitTruncator / 10);
		operandList[0] = number;
		
		number = 0;
		do {
			number = Math.abs(random.nextInt()) % 11;
		} while (exactly && number < multiplierDigitTruncator / 10 && number == 0);
		operandList[1] = number;
		
		return new Multiplication(operandList);
	}

	public int getNumberOfOperands() {		
		return this.numberOfOperands;
	}

	public char getSymbol() {		
		return 'X';
	}

	public int getNumberOfDigits() {		
		return this.multiplicandDigits;
	}

}
