package sutansums.problem.conversion.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import sutansums.problem.IProblem;

public class ConversionProblem<T extends Comparable<? super T>, U extends IUnits> implements IProblem {
	private final List<NumberUnit<T, U>> fromNumberUnits;
	private final List<U> toUnits;

	public ConversionProblem(List<NumberUnit<T, U>> fromNumberUnits, List<U> toUnits) {
		this.fromNumberUnits = new ArrayList<>(fromNumberUnits);
		this.toUnits = new ArrayList<>(toUnits);
	}

	public ConversionProblem(NumberUnit<T, U> fromNumberUnit, List<U> toUnits) {
		this.fromNumberUnits = Arrays.asList(fromNumberUnit);
		this.toUnits = toUnits;
	}

	public ConversionProblem(NumberUnit<T, U> fromNumberUnit, U toUnit) {
		this.fromNumberUnits = Arrays.asList(fromNumberUnit);
		this.toUnits = Arrays.asList(toUnit);
	}

	public List<NumberUnit<T, U>> getFrom() {
		return fromNumberUnits;
	}

	public List<U> getTo() {
		return toUnits;
	}

	@Override
	public String getHorizantalString() {
		return fromNumberUnits.stream()
				.map(i -> i.toString())
				.collect(Collectors.joining(" ")) + " = ";
	}

	@Override
	public List<String> getVerticalString() {
		return Collections.emptyList();
	}

	public static class NumberUnit<T extends Comparable<? super T>, U extends IUnits> {
		private final T number;
		private final U units;

		public NumberUnit(T number, U units) {
			this.number = number;
			this.units = units;
		}

		public NumberUnit(U units) {
			this.number = null;
			this.units = units;
		}

		public T getNumber() {
			return number;
		}

		public U getUnits() {
			return units;
		}

		@Override
		public String toString() {
			return String.valueOf(number) + " " + units.getUnitString();
		}
	}
}
