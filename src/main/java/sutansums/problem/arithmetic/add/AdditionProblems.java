package sutansums.problem.arithmetic.add;

import static sutansums.problem.arithmetic.common.ArithmeticSymbol.ADD;

import sutansums.problem.arithmetic.common.ArithmeticProblemIterator;
import sutansums.problem.generator.LongOperandsGenerator;

public final class AdditionProblems {

	private AdditionProblems() {
	}

	private static LongOperandsGenerator long_1x2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(1)
			.withNumberOfOperands(2)
			.build();
	public static ArithmeticProblemIterator<Long> long_1x2 = new ArithmeticProblemIterator<>(
			ADD,
			long_1x2_generator);

	private static LongOperandsGenerator long_1x3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(1)
			.withNumberOfOperands(3)
			.build();
	public static ArithmeticProblemIterator<Long> long_1x3 = new ArithmeticProblemIterator<>(
			ADD,
			long_1x3_generator);
	
	private static LongOperandsGenerator long_1x4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(1)
			.withNumberOfOperands(4)
			.build();
	public static ArithmeticProblemIterator<Long> long_1x4 = new ArithmeticProblemIterator<>(
			ADD,
			long_1x4_generator);
	
	private static LongOperandsGenerator long_2x2_generator_withOutCarry = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(2)
			.withValidator(new AdditionLongValidator(false /* isWithCarry */))
			.build();
	public static ArithmeticProblemIterator<Long> long_2x2_withOutCarry = new ArithmeticProblemIterator<>(
			ADD,
			long_2x2_generator_withOutCarry);

	private static LongOperandsGenerator long_2x2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(2)
			.build();
	public static ArithmeticProblemIterator<Long> long_2x2 = new ArithmeticProblemIterator<>(
			ADD,
			long_2x2_generator);

	private static LongOperandsGenerator long_2x3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(3)
			.build();
	public static ArithmeticProblemIterator<Long> long_2x3 = new ArithmeticProblemIterator<>(
			ADD,
			long_2x3_generator);

	private static LongOperandsGenerator long_2x4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(4)
			.build();
	public static ArithmeticProblemIterator<Long> long_2x4 = new ArithmeticProblemIterator<>(
			ADD,
			long_2x4_generator);
	
	private static LongOperandsGenerator long_3x2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(2)
			.build();
	public static ArithmeticProblemIterator<Long> long_3x2 = new ArithmeticProblemIterator<>(
			ADD,
			long_3x2_generator);

	private static LongOperandsGenerator long_3x3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(3)
			.build();
	public static ArithmeticProblemIterator<Long> long_3x3 = new ArithmeticProblemIterator<>(
			ADD,
			long_3x3_generator);

	private static LongOperandsGenerator long_3x4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(4)
			.build();
	public static ArithmeticProblemIterator<Long> long_3x4 = new ArithmeticProblemIterator<>(
			ADD,
			long_3x4_generator);
}
