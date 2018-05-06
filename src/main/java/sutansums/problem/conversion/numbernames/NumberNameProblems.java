package sutansums.problem.conversion.numbernames;

import sutansums.problem.conversion.common.ConversionProblemIterator;
import sutansums.problem.conversion.common.NullUnit;
import sutansums.problem.generator.LongOperandsGenerator;

public final class NumberNameProblems {

	private NumberNameProblems() {
	}

	private static LongOperandsGenerator name_2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_2 = new ConversionProblemIterator<>(
			new IndianNumberNameTransformer(name_2_generator), NullUnit.EMPTY);

	
	private static LongOperandsGenerator name_3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_3 = new ConversionProblemIterator<>(
			new IndianNumberNameTransformer(name_3_generator), NullUnit.EMPTY);

	
	private static LongOperandsGenerator name_4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(4)
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<String, NullUnit> name_4 = new ConversionProblemIterator<>(
			new IndianNumberNameTransformer(name_4_generator), NullUnit.EMPTY);

	
	private static LongOperandsGenerator long_2_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(2)
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<Long, NullUnit> long_2 = new ConversionProblemIterator<>(
			new NumberNameTransformer(long_2_generator), NullUnit.EMPTY);

	
	private static LongOperandsGenerator long_3_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(3)
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<Long, NullUnit> long_3 = new ConversionProblemIterator<>(
			new NumberNameTransformer(long_3_generator), NullUnit.EMPTY);

	
	private static LongOperandsGenerator long_4_generator = LongOperandsGenerator.builder()
			.withNumberOfDigits(4)
			.withNumberOfOperands(1)
			.build();
	public static ConversionProblemIterator<Long, NullUnit> long_4 = new ConversionProblemIterator<>(
			new NumberNameTransformer(long_4_generator), NullUnit.EMPTY);
	
}
