package sutansums.problem.conversion.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import sutansums.problem.conversion.common.ConversionProblem.NumberUnit;
import sutansums.problem.generator.IGenerator;

public class ConversionProblemIterator<T extends Comparable<? super T>, U extends IUnits>
		implements Iterator<ConversionProblem<T, U>> {
	private final IGenerator<NumberUnit<T, U>> rand;
	private final List<U> toUnits;

	public ConversionProblemIterator(IGenerator<NumberUnit<T, U>> generator, List<U> toUnits) {
		this.rand = generator;
		this.toUnits = toUnits;
	}

	public ConversionProblemIterator(IGenerator<NumberUnit<T, U>> generator, U toUnit) {
		this.rand = generator;
		this.toUnits = Arrays.asList(toUnit);
	}

	@Override
	public ConversionProblem<T, U> next() {
		List<NumberUnit<T, U>> operandList = rand.getNext();
		return new ConversionProblem<T, U>(operandList, toUnits);
	}

	@Override
	public boolean hasNext() {
		return true;
	}
}
