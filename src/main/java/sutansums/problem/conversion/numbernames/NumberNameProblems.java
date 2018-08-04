package sutansums.problem.conversion.numbernames;

import sutansums.problem.conversion.common.ConversionProblemIterator;
import sutansums.problem.conversion.common.NullUnit;
import sutansums.problem.generator.LongOperandsGenerator;

public final class NumberNameProblems {

	private NumberNameProblems() {
	}

	private static LongOperandsGenerator name_1_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(1)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_1 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IndianNumberNameTransformer(name_1_generator));

	private static LongOperandsGenerator name_2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_2 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IndianNumberNameTransformer(name_2_generator));

	private static LongOperandsGenerator name_3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_3 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IndianNumberNameTransformer(name_3_generator));

	private static LongOperandsGenerator name_4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(4)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_4 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IndianNumberNameTransformer(name_4_generator));

	private static LongOperandsGenerator long_2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<Long, NullUnit> long_2 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IdentityTransformer<Long>(long_2_generator));

	private static LongOperandsGenerator long_3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<Long, NullUnit> long_3 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IdentityTransformer<Long>(long_3_generator));

	private static LongOperandsGenerator long_4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(4)
			.withSameNoOfDigits()
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<Long, NullUnit> long_4 = new ConversionProblemIterator<>(
			NullUnit.EMPTY, new IdentityTransformer<Long>(long_4_generator));

}
