package sutansums.problem;

public class NumberNames implements IProblem<Integer> {

	private final int number;

	public NumberNames(int number) {
		this.number = number;
	}

	@Override
	public char getSymbol() {
		return ' ';
	}

	@Override
	public Integer[] getOperands() {
		return new Integer[] { number };
	}

}
