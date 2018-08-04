package sutansums.problem.conversion.numbernames;

import java.util.List;
import java.util.stream.Collectors;

import sutansums.problem.conversion.common.ConversionProblem.NumberUnit;
import sutansums.problem.conversion.common.NullUnit;
import sutansums.problem.generator.AbstractTransformer;
import sutansums.problem.generator.IGenerator;

public class IdentityTransformer<T extends Comparable<? super T>>
		extends AbstractTransformer<T, NumberUnit<T, NullUnit>> {

	public IdentityTransformer(IGenerator<T> generator) {
		super(generator);
	}

	@Override
	public List<NumberUnit<T, NullUnit>> transform(List<T> sourceList) {
		return sourceList.stream()
				.map(s -> new NumberUnit<>(s, NullUnit.EMPTY))
				.collect(Collectors.toList());
	}
}
