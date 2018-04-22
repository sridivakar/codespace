package sutansums.problem;

import java.util.Arrays;

public class Substraction implements IProblem<Integer> {
	private final Integer[] operandList;

	public Substraction(Integer[] operandList) {
		this.operandList = operandList;
		Arrays.sort(this.operandList, new DescendingOrder());
	}

	public Integer[] getOperands() {
		return this.operandList;
	}

	public char getSymbol() {
		return '-';
	}

}
