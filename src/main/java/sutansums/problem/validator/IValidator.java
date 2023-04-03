package sutansums.problem.validator;

import java.util.List;

public interface IValidator<T> {

	boolean isValid(List<T> operandList);

	default boolean isValid(T operand, int operandIndex) { return true; }

}
