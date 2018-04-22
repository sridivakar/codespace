package sutansums.problem;

public class NumberNumerals implements IProblem<String> {

	private final String number;

	public NumberNumerals(String number) {
		this.number = number;
	}

	@Override
	public char getSymbol() {
		return ' ';
	}

	@Override
	public String[] getOperands() {
		return new String[] { number };
	}

}
