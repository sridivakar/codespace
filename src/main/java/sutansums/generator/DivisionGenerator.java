package sutansums.generator;

import java.util.Random;
import sutansums.problem.Division;

public class DivisionGenerator extends AbstractGenerator<Division> {
	private final boolean exactly;
	private final Random random = new Random();

	private final int dividendDigitTruncator;
	private final int divisorDigitTruncator;

	private DivisionGenerator(Builder builder) {
		super(2, builder.dividendDigits);
		this.exactly = builder.exactly;

		int tmp = 1;
		for (int i = 1; i <= builder.dividendDigits; i++) {
			tmp *= 10;
		}
		this.dividendDigitTruncator = tmp;

		tmp = 1;
		for (int i = 1; i <= builder.divisorDigits; i++) {
			tmp *= 10;
		}
		divisorDigitTruncator = tmp;
	}

	public Division getNext() {
		int number = 0;
		do {
			number = Math.abs(random.nextInt()) % divisorDigitTruncator;
		} while (exactly && number < divisorDigitTruncator / 10 && number == 0);
		int divisor = number;

		do {
			number = Math.abs(random.nextInt()) % dividendDigitTruncator;
		} while (exactly && number < dividendDigitTruncator / 10 /* && (number % divisor) == 0 */);
		int dividend = number;

		Integer[] operandList = new Integer[] { divisor, dividend };
		return new Division(operandList);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private int dividendDigits;
		private int divisorDigits;
		private boolean exactly;

		private Builder() {
		}

		public Builder withDividendDigits(int dividendDigits) {
			this.dividendDigits = dividendDigits;
			return this;
		}

		public Builder withDivisorDigits(int divisorDigits) {
			this.divisorDigits = divisorDigits;
			return this;
		}

		public Builder withSameNoOfDigits() {
			this.exactly = true;
			return this;
		}

		public Builder withDiffNoOfDigits() {
			this.exactly = false;
			return this;
		}

		public DivisionGenerator build() {
			return new DivisionGenerator(this);
		}
	}

}
