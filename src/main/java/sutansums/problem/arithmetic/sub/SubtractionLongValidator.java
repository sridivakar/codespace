package sutansums.problem.arithmetic.sub;

import java.util.List;

import sutansums.problem.validator.IValidator;

public class SubtractionLongValidator implements IValidator<Long> {
	private final boolean isWithBorrow;

	public SubtractionLongValidator(boolean isWithBorrow) {
		this.isWithBorrow = isWithBorrow;
	}

	public boolean isValid(List<Long> operandList) {
		if (!isWithBorrow) {
			int div = 1;
			boolean allDigitsZero = false;
			while (!allDigitsZero) {
				allDigitsZero = true;

				Long firstOperand = operandList.get(0);
				Long firstDigit = (firstOperand / div) % 10;

				Long secondOperand = operandList.get(1);
				Long secondDigit = (secondOperand / div) % 10;

				if ((firstOperand / div) != 0 && (secondOperand / div) != 0) {
					allDigitsZero = false;
				}

				if (firstDigit < secondDigit) {
					return false;
				}

				div *= 10;
			}
		}
		return true;
	}

}