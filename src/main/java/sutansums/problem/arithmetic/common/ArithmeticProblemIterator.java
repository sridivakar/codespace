package sutansums.problem.arithmetic.common;

import java.util.Iterator;
import java.util.List;

import sutansums.problem.generator.IGenerator;

public class ArithmeticProblemIterator<T extends Comparable<? super T>>
		implements Iterator<ArithmeticProblem<T>> {
	private final ArithmeticSymbol symbol;
	private final IGenerator<T> generator;

	public ArithmeticProblemIterator(ArithmeticSymbol symbol, IGenerator<T> generator) {
		this.symbol = symbol;
		this.generator = generator;
	}

	@Override
	public ArithmeticProblem<T> next() {
		List<T> operandList = generator.getNext();
		return new ArithmeticProblem<T>(symbol, operandList);
	}

	@Override
	public boolean hasNext() {
		return true;
	}
}
