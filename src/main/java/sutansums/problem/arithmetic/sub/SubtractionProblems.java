package sutansums.problem.arithmetic.sub;

import static sutansums.problem.arithmetic.common.ArithmeticSymbol.SUB;

import sutansums.problem.arithmetic.common.ArithmeticProblemIterator;
import sutansums.problem.generator.LongOperandsGenerator;

public final class SubtractionProblems {

	private SubtractionProblems() {
	}

	private static LongOperandsGenerator long_1x2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(1)
			.withNumberOfOperands(2)
			.build();
	public static ArithmeticProblemIterator<Long> long_1x2 = new ArithmeticProblemIterator<>(
			SUB,
			long_1x2_generator);

	private static LongOperandsGenerator long_2x2_generator_withOutBorrow = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(2)
			.withValidator(new SubtractionLongValidator(false /* isWithBorrow */))
			.build();
	public static ArithmeticProblemIterator<Long> long_2x2_withOutBorrow = new ArithmeticProblemIterator<>(
			SUB,
			long_2x2_generator_withOutBorrow);

	private static LongOperandsGenerator long_2x2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(2)
			.build();
	public static ArithmeticProblemIterator<Long> long_2x2 = new ArithmeticProblemIterator<>(
			SUB,
			long_2x2_generator);

	private static LongOperandsGenerator long_3x2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(2)
			.build();
	public static ArithmeticProblemIterator<Long> long_3x2 = new ArithmeticProblemIterator<>(
			SUB,
			long_3x2_generator);

	private static LongOperandsGenerator long_3x3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(3)
			.build();
	public static ArithmeticProblemIterator<Long> long_3x3 = new ArithmeticProblemIterator<>(
			SUB,
			long_3x3_generator);

}
