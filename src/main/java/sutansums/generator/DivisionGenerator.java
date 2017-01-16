package sutansums.generator;

import java.util.Random;

import sutansums.problem.Division;

public class DivisionGenerator implements IGenerator <Division> {
	private final int numberOfOperands;
	private final int dividendDigits;
	private final int divisorDigits;
	private final boolean exactly;
	private final Random random;

	public DivisionGenerator(int dividendDigits, int divisorDigits, boolean exactly) {
		this.numberOfOperands = 2;
		this.dividendDigits = dividendDigits;
		this.divisorDigits = divisorDigits;
		this.exactly = exactly;
		this.random = new Random();
	}

	public Division getNext() {
		int dividendDigitTruncator = 1;
		for (int i = 1; i <= dividendDigits; i++) {
			dividendDigitTruncator *= 10;
		}
		
		int divisorDigitTruncator = 1;
		for (int i = 1; i <= divisorDigits; i++) {
			divisorDigitTruncator *= 10;
		}
	
		int number = 0;
		do {
			number = Math.abs(random.nextInt()) % divisorDigitTruncator;
		} while (exactly && number < divisorDigitTruncator / 10 && number == 0);
		int divisor = number;		
		
		do {
			number = Math.abs(random.nextInt()) % dividendDigitTruncator;
		} while (exactly && number < dividendDigitTruncator / 10 /*&& (number % divisor) == 0*/);
		int dividend = number;
		
		Integer[] operandList = new Integer[] {divisor, dividend};
		return new Division(operandList);
	}

	public int getNumberOfOperands() {		
		return this.numberOfOperands;
	}

	public char getSymbol() {		
		return ')';
	}

	public int getNumberOfDigits() {		
		return this.dividendDigits;
	}

}
