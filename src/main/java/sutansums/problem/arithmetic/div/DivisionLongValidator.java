package sutansums.problem.arithmetic.div;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sutansums.problem.validator.IValidator;

public class DivisionLongValidator implements IValidator<Long> {
	private final List<Integer> tables;

	public DivisionLongValidator(List<Integer> tables) {
		this.tables = Collections.unmodifiableList(tables);
	}

	public boolean isValid(List<Long> operandList) {
		Long divisor = operandList.get(0);

		if (divisor == 0) {
			return false;
		}

		// Validate Tables
		if (tables != null && tables.size() > 0) {
			Set<Integer> tableSet = new HashSet<>(tables);

			int div = 1;
			while ((divisor / div) > 0) {
				int digit = (int) (divisor / div) % 10;

				if (!tableSet.contains(digit)) {
					return false;
				}
				div *= 10;
			}
		}

		return true;
	}
}