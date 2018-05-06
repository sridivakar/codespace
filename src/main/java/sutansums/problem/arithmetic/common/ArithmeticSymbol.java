package sutansums.problem.arithmetic.common;

public enum ArithmeticSymbol {

	ADD('+'),

	SUB('-'),

	MUL('x'),

	DIV(')'),

	;

	private final char symbol;

	ArithmeticSymbol(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

}
