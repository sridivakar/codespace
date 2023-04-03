package sutansums.problem.arithmetic.mul;

import java.util.*;

import sutansums.problem.validator.IValidator;

public class MultiplicationLongValidator implements IValidator<Long> {
	private final Set<Integer> tables;

	public MultiplicationLongValidator(Integer table) {
		this.tables = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(table)));
	}

	public MultiplicationLongValidator(List<Integer> tables) {
		this.tables = Collections.unmodifiableSet(new HashSet<>(tables));
	}

	public boolean isValid(Long operand, int operandIndex) {
		if(operandIndex != 1) {
			return true;
		}
		Long multiplier = operand;
		if (tables != null && tables.size() > 0) {
			if (tables.size() == 1) {
				if(!tables.contains(multiplier.intValue())) {
					return false;
				}
			} else {
				int div = 1;

				while ((multiplier / div) > 0) {
					int digit = (int) (multiplier / div) % 10;

					if (!tables.contains(digit)) {
						return false;
					}
					div *= 10;
				}
			}
		}

		return true;
	}
	public boolean isValid(List<Long> operandList) {
		if (operandList.get(0) < operandList.get(1)) {
			return false;
		}

		return true;
		/*
		Long multiplier = operandList.get(1);
		if (tables != null && tables.size() > 0) {
			if (tables.size() == 1) {
				if(!tables.contains(multiplier)) {
					return false;
				}
			} else {
				int div = 1;

				while ((multiplier / div) > 0) {
					int digit = (int) (multiplier / div) % 10;

					if (!tables.contains(digit)) {
						return false;
					}
					div *= 10;
				}
			}
		}

		return true;
		*/

	}
}