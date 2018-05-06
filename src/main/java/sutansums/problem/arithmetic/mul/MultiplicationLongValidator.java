package sutansums.problem.arithmetic.mul;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sutansums.problem.validator.IValidator;

public class MultiplicationLongValidator implements IValidator<Long> {
	private final Set<Integer> tables;

	public MultiplicationLongValidator(List<Integer> tables) {
		this.tables = Collections.unmodifiableSet(new HashSet<>(tables));
	}

	public boolean isValid(List<Long> operandList) {
		Long multiplier = operandList.get(1);
		if (tables != null && tables.size() > 0) {
			int div = 1;
			while ((multiplier / div) > 0) {
				int digit = (int) (multiplier / div) % 10;

				if (!tables.contains(digit)) {
					return false;
				}
				div *= 10;
			}
		}

		return true;
	}
}