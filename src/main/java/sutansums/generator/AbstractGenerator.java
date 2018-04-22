package sutansums.generator;

import sutansums.problem.IProblem;

public abstract class AbstractGenerator<P extends IProblem<?>> implements IGenerator<P> {
	protected final int numberOfOperands;
	protected final int numberOfDigits;

	AbstractGenerator(int numberOfOperands, int numberOfDigits) {
		this.numberOfOperands = numberOfOperands;
		this.numberOfDigits = numberOfDigits;
	}

	@Override
	public int getNumberOfOperands() {
		return this.numberOfOperands;
	}

	@Override
	public int getNumberOfDigits() {
		return this.numberOfDigits;
	}

}
