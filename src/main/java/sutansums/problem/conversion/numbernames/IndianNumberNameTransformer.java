package sutansums.problem.conversion.numbernames;

import java.util.List;
import java.util.stream.Collectors;

import sutansums.problem.conversion.common.ConversionProblem.NumberUnit;
import sutansums.problem.conversion.common.NullUnit;
import sutansums.problem.generator.AbstractTransformer;
import sutansums.problem.generator.IGenerator;

public class IndianNumberNameTransformer extends AbstractTransformer<Long, NumberUnit<String, NullUnit>> {

	public IndianNumberNameTransformer(IGenerator<Long> generator) {
		super(generator);
	}

	@Override
	public List<NumberUnit<String, NullUnit>> transform(List<Long> sourceList) {
		return sourceList.stream()
				.map(number -> NumberNameUtil.getIndianNumberName(number))
				.map(s -> new NumberUnit<>(s, NullUnit.EMPTY))
				.collect(Collectors.toList());
	}

}
