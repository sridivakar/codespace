package sutansums.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import sutansums.problem.Multiplication;


public class MultiplicationGenerator implements IGenerator<Multiplication> {
    private final int numberOfOperands = 2;
    private final int multiplicandDigits;
    private final int multiplierDigits;
    private final boolean isExactNumberOfDigits;
    private final List<Integer> tables;
    private final Random random = new Random();

    private MultiplicationGenerator(Builder builder) {
        this.multiplicandDigits = builder.multiplicandDigits;
        this.multiplierDigits = builder.multiplierDigits;
        this.isExactNumberOfDigits = builder.isExactNumberOfDigits;
        this.tables = builder.tables;
    }

    public Multiplication getNext() {
        int multiplicandDigitTruncator = 1;
        for (int i = 1; i <= multiplicandDigits; i++) {
            multiplicandDigitTruncator *= 10;
        }

        int multiplierDigitTruncator = 1;
        for (int i = 1; i <= multiplierDigits; i++) {
            multiplierDigitTruncator *= 10;
        }

        Integer[] operandList = new Integer[numberOfOperands];
        int number = 0;
        do {
            number = Math.abs(random.nextInt()) % multiplicandDigitTruncator;
        }
        while (isExactNumberOfDigits && number < multiplicandDigitTruncator / 10);
        operandList[0] = number;

        do {
            number = 0;
            do {
                number = Math.abs(random.nextInt()) % 11;
            }
            while (isExactNumberOfDigits && number < multiplierDigitTruncator / 10 && number == 0);
            operandList[1] = number;
        } while(!isValid(number));
        
        return new Multiplication(operandList);
    }

    private boolean isValid(Integer multiplier) {
        if (tables != null && tables.size() > 0) {
            Set<Integer> tableSet = new HashSet<>(tables);

            int div = 1;
            while ((multiplier / div) > 0) {
                int digit = (multiplier / div) % 10;

                if (!tableSet.contains(digit)) {
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
        return 'X';
    }

    public int getNumberOfDigits() {
        return this.multiplicandDigits;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int multiplicandDigits;
        private int multiplierDigits;
        private boolean isExactNumberOfDigits;
        private List<Integer> tables;

        private Builder() {
        }

        public Builder withMultiplicandDigits(int multiplicandDigits) {
            this.multiplicandDigits = multiplicandDigits;
            return this;
        }

        public Builder withMultiplierDigits(int multiplierDigits) {
            this.multiplierDigits = multiplierDigits;
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

        public Builder withTables(List<Integer> tables) {
            this.tables = tables;
            return this;
        }

        public MultiplicationGenerator build() {
            return new MultiplicationGenerator(this);
        }
    }

}
