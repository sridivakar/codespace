package sutansums.problem;

import java.util.Arrays;

public class Division implements IProblem<Integer> {
	private final Integer[] operandList;

	public Division(Integer[] operandList) {
		this.operandList = operandList;
		Arrays.sort(this.operandList);
	}

	public Integer[] getOperands() {
		return this.operandList;
	}

	public char getSymbol() {
		return ')';
	}

}
