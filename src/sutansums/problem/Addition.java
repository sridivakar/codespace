package sutansums.problem;


public class Addition implements IProblem {
	private final Integer[] operandList;

	public Addition(Integer[] operandList) {		
		this.operandList = operandList;
		//Arrays.sort(this.operandList, new DescendingOrder());
	}

	public Integer[] getOperands() {
		return this.operandList;
	}

	public char getSymbol() {
		return '+';
	}

}
