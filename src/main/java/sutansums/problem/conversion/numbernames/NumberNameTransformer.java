package sutansums.problem.conversion.numbernames;

import java.util.List;
import java.util.stream.Collectors;

import sutansums.problem.conversion.common.ConversionProblem.NumberUnit;
import sutansums.problem.conversion.common.NullUnit;
import sutansums.problem.generator.AbstractTransformer;
import sutansums.problem.generator.IGenerator;

public class NumberNameTransformer extends AbstractTransformer<Long, NumberUnit<Long, NullUnit>> {

	public NumberNameTransformer(IGenerator<Long> generator) {
		super(generator);
	}

	@Override
	public List<NumberUnit<Long, NullUnit>> transform(List<Long> sourceList) {
		return sourceList.stream()
				.map(s -> new NumberUnit<>(s, NullUnit.EMPTY))
				.collect(Collectors.toList());
	}
}
