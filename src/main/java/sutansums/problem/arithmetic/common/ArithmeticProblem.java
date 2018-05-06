package sutansums.problem.arithmetic.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import sutansums.problem.IProblem;

public class ArithmeticProblem<T extends Comparable<? super T>> implements IProblem {
	private final List<T> operandList;
	private final ArithmeticSymbol symbol;

	public ArithmeticProblem(ArithmeticSymbol symbol, List<T> operandList) {
		this.symbol = symbol;
		this.operandList = new ArrayList<>(operandList);
	}

	public List<T> getOperands() {
		return this.operandList;
	}

	public int getNumberOfDigits() {
		Optional<Integer> maxSize = operandList
				.stream()
				.map(n -> Integer.valueOf(String.valueOf(n).length()))
				.max(java.util.Comparator.naturalOrder());
				
		return maxSize.get();
	}

	public ArithmeticSymbol getSymbol() {
		return symbol;
	}

	@Override
	public String getHorizantalString() {
		String seperator = " " + String.valueOf(getSymbol().getSymbol()) + " ";
		return getOperands().stream()
				.map(i -> i.toString())
				.collect(Collectors.joining(seperator)) + " = ";
	}

	@Override
	public List<String> getVerticalString() {
		List<String> returnValue = new ArrayList<>();
		StringBuilder builder = new StringBuilder();

		// Prepare a dash line depending up on number of digits
		builder.setLength(0);
		for (int k = 0; k < getNumberOfDigits() + 1; k++) {
			builder.append("_");
		}
		String resultLine = builder.toString();

		// Print all problems that fit in a row
		for (int k = 0; k < getOperands().size(); k++) {
			builder.setLength(0);

			// put either a space or symbol for every line
			if (k == getOperands().size() - 1) {
				builder.append(getSymbol().getSymbol());
			} else {
				builder.append(" ");
			}

			// format number to have leading spaces if number has less digits
			String number = String.valueOf(getOperands().get(k));
			char[] spaces = new char[getNumberOfDigits() - number.length()];
			Arrays.fill(spaces, ' ');
			number = new String(spaces) + number;
			builder.append(number);

			returnValue.add(builder.toString());
		}

		// print lines for writing result
		returnValue.add(resultLine);
		returnValue.add("  ");
		returnValue.add(resultLine);

		return returnValue;
	}

}
