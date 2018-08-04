package sutansums.problem.arithmetic.common;

public enum ArithmeticSymbol {

	ADD("+"),

	SUB("-"),

	MUL("x"),

	DIV(")"),

	;

	private final String symbol;

	ArithmeticSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
