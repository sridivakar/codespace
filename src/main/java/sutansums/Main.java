package sutansums;

import java.util.Arrays;
import sutansums.generator.AdditionGenerator;
import sutansums.generator.DivisionGenerator;
import sutansums.generator.MultiplicationGenerator;
import sutansums.generator.SubtractionGenerator;


public class Main {
    protected static final AdditionGenerator additionGenerator_2X1_ = AdditionGenerator.builder()
            .withOperands(2).withDigits(1).build();
    protected static final AdditionGenerator additionGenerator_3X1_ = AdditionGenerator.builder()
            .withOperands(3).withDigits(1).withCarry().build();
    protected static final AdditionGenerator additionGenerator_3X2_ = AdditionGenerator.builder()
            .withOperands(3).withDigits(2).build();
    protected static final AdditionGenerator additionGenerator_2X2_withSameNoOfDigits = AdditionGenerator.builder()
            .withOperands(2).withDigits(2).withCarry().withSameNoOfDigits().build();
    protected static final AdditionGenerator additionGenerator_2X2_withDifferentNoOfDigits = AdditionGenerator.builder()
            .withOperands(2).withDigits(2).withCarry().withDiffNoOfDigits().build();    
    protected static final AdditionGenerator additionGenerator_3X3_withSameNoOfDigits = AdditionGenerator.builder()
            .withOperands(3).withDigits(3).withCarry().withSameNoOfDigits().build();
    
    protected static final AdditionGenerator additionGenerator_3X3_ = AdditionGenerator.builder()
            .withOperands(3).withDigits(3).withCarry().build();
    protected static final AdditionGenerator additionGenerator_4X3_ = AdditionGenerator.builder()
            .withOperands(4).withDigits(3).withCarry().build();
    protected static final AdditionGenerator additionGenerator_4X4_ = AdditionGenerator.builder()
            .withOperands(4).withDigits(3).withCarry().build();
    protected static final AdditionGenerator additionGenerator_5X4_ = AdditionGenerator.builder()
            .withOperands(5).withDigits(3).withCarry().build();

    protected static final SubtractionGenerator subtractionGenerator_1_ = SubtractionGenerator.builder()
            .withNumberOfDigits(1).build();
    protected static final SubtractionGenerator subtractionGenerator_2_withOutBorrow = SubtractionGenerator.builder()
            .withNumberOfDigits(2).withDiffNoOfDigits().withOutBorrow().build();
    protected static final SubtractionGenerator subtractionGenerator_2_ = SubtractionGenerator.builder()
            .withNumberOfDigits(2).withDiffNoOfDigits().build();
    protected static final SubtractionGenerator subtractionGenerator_3_ = SubtractionGenerator.builder()
            .withNumberOfDigits(3).withDiffNoOfDigits().build();
    protected static final SubtractionGenerator subtractionGenerator_4_ = SubtractionGenerator.builder()
            .withNumberOfDigits(4).withDiffNoOfDigits().build();
    protected static final SubtractionGenerator subtractionGenerator_5_ = SubtractionGenerator.builder()
            .withNumberOfDigits(5).withDiffNoOfDigits().build();

    protected static final MultiplicationGenerator multiplicationGenerator_1_tablesLessthan5 = MultiplicationGenerator.builder()
            .withMultiplicandDigits(1).withMultiplierDigits(1).withDiffNoOfDigits().withTables(Arrays.asList(1,2,3,4,5)).build();
    protected static final MultiplicationGenerator multiplicationGenerator_2 = MultiplicationGenerator.builder()
            .withMultiplicandDigits(2).withMultiplierDigits(1).withDiffNoOfDigits().build();
    protected static final MultiplicationGenerator multiplicationGenerator_3 = MultiplicationGenerator.builder()
            .withMultiplicandDigits(3).withMultiplierDigits(1).withDiffNoOfDigits().build();

    protected static final DivisionGenerator divisionGenerator_2 = DivisionGenerator.builder()
            .withDividendDigits(2).withDivisorDigits(1).withDiffNoOfDigits().build();
    protected static final DivisionGenerator divisionGenerator_3 = DivisionGenerator.builder()
            .withDividendDigits(3).withDivisorDigits(1).withDiffNoOfDigits().build();
    protected static final DivisionGenerator divisionGenerator_4 = DivisionGenerator.builder()
            .withDividendDigits(4).withDivisorDigits(1).withSameNoOfDigits().build();

}
