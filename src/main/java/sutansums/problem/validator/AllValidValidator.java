package sutansums.problem.validator;

import java.util.List;

public class AllValidValidator<T> implements IValidator<T> {

	@Override
	public boolean isValid(List<T> operandList) {
		return true;
	}

}
