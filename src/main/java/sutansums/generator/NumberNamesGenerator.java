package sutansums.generator;

import java.util.Random;

import sutansums.problem.NumberNames;

public class NumberNamesGenerator extends AbstractGenerator<NumberNames> {
	private final boolean isExactNumberOfDigits;
	private final Random random = new Random();

	private final int digitTruncator;

	private NumberNamesGenerator(Builder builder) {
		super(1, builder.numberOfDigits);
		this.isExactNumberOfDigits = builder.isExactNumberOfDigits;

		int tmp = 1;
		for (int i = 1; i <= builder.numberOfDigits; i++) {
			tmp *= 10;
		}
		this.digitTruncator = tmp;
	}

	@Override
	public NumberNames getNext() {
		Integer number = Integer.valueOf(0);
		do {
			for (int j = 0; j < numberOfOperands; j++) {
				do {
					number = Math.abs(random.nextInt()) % digitTruncator;
				} while ((isExactNumberOfDigits && number < digitTruncator / 10));
			}
		} while (!isValid(number));
		return new NumberNames(number);
	}

	private boolean isValid(Integer number) {
		return true;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int numberOfDigits;
		private boolean isExactNumberOfDigits = false;

		private Builder() {
		}

		public Builder withDigits(int numberOfDigits) {
			this.numberOfDigits = numberOfDigits;
			return this;
		}

		public Builder withSameNoOfDigits() {
			this.isExactNumberOfDigits = true;
			return this;
		}

		public Builder withDiffNoOfDigits() {
			this.isExactNumberOfDigits = false;
			return this;
		}

		public NumberNamesGenerator build() {
			return new NumberNamesGenerator(this);
		}
	}
}
