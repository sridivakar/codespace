package sutansums.problem.arithmetic.add;

import java.util.List;

import sutansums.problem.validator.IValidator;

public class AdditionLongValidator implements IValidator<Long> {
	private final boolean isWithCarry;

	public AdditionLongValidator(boolean isWithCarry) {
		this.isWithCarry = isWithCarry;
	}

	public boolean isValid(List<Long> operandList) {
		if (isWithCarry) {
			return true;
		}

		return applyNoCarryRule(operandList);
	}

	private boolean applyNoCarryRule(List<Long> operandList) {
		int div = 1;
		boolean allDigitsZero = false;
		while (!allDigitsZero) {
			int sumOfDigits = 0;
			allDigitsZero = true;
			for (Long operand : operandList) {
				long digit = (operand / div) % 10;
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

		return true;
	}

}