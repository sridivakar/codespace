package sutansums.generator;

import java.util.Random;
import sutansums.problem.Substraction;


public class SubtractionGenerator implements IGenerator<Substraction> {
    private final int numberOfOperands = 2;
    private final int numberOfDigits;
    private final boolean isExactNumberOfDigits;
    private final boolean isWithBorrow;
    private final Random random = new Random();

    private SubtractionGenerator(Builder builder) {
        this.numberOfDigits = builder.numberOfDigits;
        this.isExactNumberOfDigits = builder.isExactNumberOfDigits;
        this.isWithBorrow = builder.isWithBorrow;
    }

    public Substraction getNext() {
        int digitTruncator = 1;
        for (int i = 1; i <= numberOfDigits; i++) {
            digitTruncator *= 10;
        }

        Integer[] operandList;
        do {
            operandList = new Integer[numberOfOperands];
            for (int j = 0; j < numberOfOperands; j++) {
                int number = 0;
                do {
                    number = Math.abs(random.nextInt()) % digitTruncator;
                }
                while (isExactNumberOfDigits && number < digitTruncator / 10);
                operandList[j] = number;
            }
        }
        while (!isValid(operandList));
        return new Substraction(operandList);
    }

    private boolean isValid(Integer[] operandList) {
        if (!isWithBorrow) {
            int div = 1;
            boolean allDigitsZero = false;
            while (!allDigitsZero) {
                allDigitsZero = true;

                Integer firstOperand = operandList[0];
                int firstDigit = (firstOperand / div) % 10;

                Integer secondOperand = operandList[1];
                int secondDigit = (secondOperand / div) % 10;

                if ((firstOperand / div) != 0 && (secondOperand / div) != 0) {
                    allDigitsZero = false;
                }

                if (firstDigit < secondDigit) {
                    return false;
                }

                div *= 10;
            }
        }
        return true;
    }

    public int getNumberOfOperands() {
        return this.numberOfOperands;
    }

    public char getSymbol() {
        return '-';
    }

    public int getNumberOfDigits() {
        return this.numberOfDigits;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int numberOfDigits;
        private boolean isExactNumberOfDigits = false;
        private boolean isWithBorrow = false;

        private Builder() {
        }

        public Builder withNumberOfDigits(int numberOfDigits) {
            this.numberOfDigits = numberOfDigits;
            return this;
        }

        public Builder withSameNoOfDigits() {
            this.isExactNumberOfDigits = true;
            return this;
        }

        public Builder withDiffNoOfDigits() {
            this.isExactNumberOfDigits = false;
            return this;
        }

        public Builder withBorrow() {
            this.isWithBorrow = true;
            return this;
        }

        public Builder withOutBorrow() {
            this.isWithBorrow = false;
            return this;
        }

        public SubtractionGenerator build() {
            return new SubtractionGenerator(this);
        }
    }

}