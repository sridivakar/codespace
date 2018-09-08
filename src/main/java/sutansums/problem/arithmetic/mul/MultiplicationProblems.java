package sutansums.problem.arithmetic.mul;

import static sutansums.problem.arithmetic.common.ArithmeticSymbol.MUL;

import java.util.Arrays;

import sutansums.problem.arithmetic.common.ArithmeticProblemIterator;
import sutansums.problem.generator.LongOperandsGenerator;

public final class MultiplicationProblems {

	private MultiplicationProblems() {
	}

	private static MultiplicationLongValidator validator = new MultiplicationLongValidator(
			Arrays.asList(1, 2, 3, 4, 5, 6, 7));

	private static LongOperandsGenerator long_1x1_generator = LongOperandsGenerator.builder()
			.withNumberOfOperands(2)
			.withMultiplicandDigits(1)
			.withMultiplierDigits(1)
			.withValidator(validator)
			.build();
	public static ArithmeticProblemIterator<Long> long_1x1 = new ArithmeticProblemIterator<>(
			MUL,
			long_1x1_generator);

	private static LongOperandsGenerator long_2x1_generator = LongOperandsGenerator.builder()
			.withNumberOfOperands(2)
			.withMultiplicandDigits(2)
			.withMultiplierDigits(1)
			.withValidator(validator)
			.build();
	public static ArithmeticProblemIterator<Long> long_2x1 = new ArithmeticProblemIterator<>(
			MUL,
			long_2x1_generator);

	private static LongOperandsGenerator long_3x1_generator = LongOperandsGenerator.builder()
			.withNumberOfOperands(2)
			.withMultiplicandDigits(3)
			.withMultiplierDigits(1)
			.build();
	public static ArithmeticProblemIterator<Long> long_3x1 = new ArithmeticProblemIterator<>(
			MUL,
			long_3x1_generator);

	private static LongOperandsGenerator long_4x1_generator = LongOperandsGenerator.builder()
			.withNumberOfOperands(2)
			.withMultiplierDigits(1)
			.withMultiplicandDigits(4)
			.build();
	public static ArithmeticProblemIterator<Long> long_4x1 = new ArithmeticProblemIterator<>(
			MUL,
			long_4x1_generator);

	private static LongOperandsGenerator long_5x1_generator = LongOperandsGenerator.builder()
			.withNumberOfOperands(2)
			.withMultiplicandDigits(5)
			.withMultiplierDigits(1)
			.build();
	public static ArithmeticProblemIterator<Long> long_5x1 = new ArithmeticProblemIterator<>(
			MUL,
			long_5x1_generator);

}
