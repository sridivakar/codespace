package sutansums.problem.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Generated;

import sutansums.problem.validator.AllValidValidator;
import sutansums.problem.validator.IValidator;

public class LongOperandsGenerator implements IGenerator<Long> {
	protected final int numberOfOperands;
	protected final List<Integer> numberOfDigitsList;
	private final boolean isExactNumberOfDigits;
	private final IValidator<Long> validator;

	@Generated("SparkTools")
	private LongOperandsGenerator(Builder builder) {
		this.numberOfOperands = builder.numberOfOperands;
		this.numberOfDigitsList = Collections.unmodifiableList(builder.numberOfDigitsList);
		this.isExactNumberOfDigits = builder.isExactNumberOfDigits;
		AllValidValidator<Long> allValidValidator = new AllValidValidator<Long>();
		this.validator = Optional.ofNullable(builder.validator).orElse(allValidValidator);
	}

	@Override
	public List<Long> getNext() {
		List<Long> operandList = new ArrayList<>(numberOfOperands);
		do {
			operandList.clear();
			for (int operandIndex = 0; operandIndex < numberOfOperands; operandIndex++) {
				long digitTruncator = 1;
				for (int i = 1; i <= getNumberOfDigits(operandIndex); i++) {
					digitTruncator *= 10;
				}

				Long number = 0L;
				do {
					number = Math.abs(random.nextLong()) % digitTruncator;
				} while ((isExactNumberOfDigits && number < digitTruncator / 10));
				operandList.add(number);
			}
		} while (!validator.isValid(operandList));

		return operandList;
	}

	private int getNumberOfDigits(int operandIndex) {
		if (operandIndex < numberOfDigitsList.size()) {
			return numberOfDigitsList.get(operandIndex);
		}

		return numberOfDigitsList.get(numberOfDigitsList.size() - 1);
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private int numberOfOperands;
		protected final List<Integer> numberOfDigitsList = new ArrayList<>();;
		private boolean isExactNumberOfDigits;
		private IValidator<Long> validator;

		private Builder() {
		}

		public Builder withNumberOfOperands(int numberOfOperands) {
			this.numberOfOperands = numberOfOperands;
			return this;
		}

		public Builder withNumberOfDigits(int numberOfDigits) {
			this.numberOfDigitsList.add(numberOfDigits);
			return this;
		}

		public Builder withMultiplicandDigits(int multiplicandDigits) {
			if (numberOfDigitsList.size() == 0) {
				this.numberOfDigitsList.add(multiplicandDigits);
			} else {
				this.numberOfDigitsList.set(0, multiplicandDigits);
			}
			return this;
		}

		public Builder withMultiplierDigits(int multiplierDigits) {
			if (numberOfDigitsList.size() == 0) {
				this.numberOfDigitsList.add(1);
				this.numberOfDigitsList.add(multiplierDigits);
			}
			if (numberOfDigitsList.size() == 1) {
				this.numberOfDigitsList.add(multiplierDigits);
			} else {
				this.numberOfDigitsList.set(0, multiplierDigits);
			}
			return this;
		}

		public Builder withDivisorDigits(int divisorDigits) {
			if (numberOfDigitsList.size() == 0) {
				this.numberOfDigitsList.add(divisorDigits);
			} else {
				this.numberOfDigitsList.set(0, divisorDigits);
			}

			return this;
		}

		public Builder withDividendDigits(int dividendDigits) {
			if (numberOfDigitsList.size() == 0) {
				this.numberOfDigitsList.add(1);
				this.numberOfDigitsList.add(dividendDigits);
			}
			if (numberOfDigitsList.size() == 1) {
				this.numberOfDigitsList.add(dividendDigits);
			} else {
				this.numberOfDigitsList.set(0, dividendDigits);
			}
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

		public Builder withValidator(IValidator<Long> validator) {
			this.validator = validator;
			return this;
		}

		public LongOperandsGenerator build() {
			return new LongOperandsGenerator(this);
		}
	}
}
