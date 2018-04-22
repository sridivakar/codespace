package sutansums.problem;

public class Addition implements IProblem<Integer> {
	private final Integer[] operandList;

	public Addition(Integer[] operandList) {
		this.operandList = operandList;
	}

	public Integer[] getOperands() {
		return this.operandList;
	}

	public char getSymbol() {
		return '+';
	}

}
