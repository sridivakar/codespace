package sutansums.generator;

import java.util.Random;
import sutansums.problem.Addition;

public class AdditionGenerator extends AbstractGenerator<Addition> {
	private final boolean isExactNumberOfDigits;
	private final boolean isWithCarry;
	private final Random random = new Random();

	private final int digitTruncator;

	private AdditionGenerator(Builder builder) {
		super(builder.numberOfOperands, builder.numberOfDigits);
		this.isExactNumberOfDigits = builder.isExactNumberOfDigits;
		this.isWithCarry = builder.isWithCarry;

		int tmp = 1;
		for (int i = 1; i <= builder.numberOfDigits; i++) {
			tmp *= 10;
		}
		this.digitTruncator = tmp;
	}

	@Override
	public Addition getNext() {
		Integer[] operandList;
		do {
			operandList = new Integer[numberOfOperands];
			for (int j = 0; j < numberOfOperands; j++) {
				int number = 0;
				do {
					number = Math.abs(random.nextInt()) % digitTruncator;
				} while ((isExactNumberOfDigits && number < digitTruncator / 10));
				operandList[j] = number;
			}
		} while (!isValid(operandList));
		return new Addition(operandList);
	}

	private boolean isValid(Integer[] operandList) {
		if (!isWithCarry) {
			int div = 1;
			boolean allDigitsZero = false;
			while (!allDigitsZero) {
				int sumOfDigits = 0;
				allDigitsZero = true;
				for (int operand : operandList) {
					int digit = (operand / div) % 10;
					sumOfDigits += digit;

					if ((operand / div) != 0) {
						allDigitsZero = false;
					}
				}
				if (sumOfDigits > 9) {
					return false;
				}
				div *= 10;
			}
		}
		return true;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int numberOfOperands;
		private int numberOfDigits;
		private boolean isExactNumberOfDigits = false;
		private boolean isWithCarry = false;

		private Builder() {

		}

		public Builder withOperands(int numberOfOperands) {
			this.numberOfOperands = numberOfOperands;
			return this;
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

		public Builder withCarry() {
			this.isWithCarry = true;
			return this;
		}

		public Builder withOutCarry() {
			this.isWithCarry = false;
			return this;
		}

		public AdditionGenerator build() {
			return new AdditionGenerator(this);
		}
	}
}
