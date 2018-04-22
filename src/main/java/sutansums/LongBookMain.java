package sutansums;

import sutansums.writer.LongBookProblemPrinter;

public class LongBookMain extends Main {
	private static final boolean isMarkDown = true;
	private static final LongBookProblemPrinter printer = new LongBookProblemPrinter(isMarkDown);

	public static void main(String[] args) {
		all2X1Additions();
		all2X1AdditionsSubtractions();
		all2X1and3X1AdditionsSubtractions();
		allAdd2X1_3X1_2X2();
		all2X2and3X2AdditionsSubtractions();
		allSubtractions();
		allAddition2x2_3X2();
		allAdd2X2Sub2();
		allSubMulDiv();
		allInOne();

		printer.close();
	}

	private static void allAdd2X2Sub2() {
		for (int i = 0; i < 5; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X2_withSameNoOfDigits, 15 /* problemAlongX */, 5 /* problemAlongY */);
			printer.writeBody(subtractionGenerator_2_, 15 /* problemAlongX */, 4 /* problemAlongY */);
			printer.writeFooter();
		}
	}

	private static void allAdd2X1_3X1_2X2() {
		for (int i = 0; i < 100; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X1_, 8 /* problemAlongX */, 1 /* problemAlongY */);
			printer.writeBody(additionGenerator_3X1_, 8 /* problemAlongX */, 1 /* problemAlongY */);
			printer.writeBody(additionGenerator_2X2_withSameNoOfDigits, 6 /* problemAlongX */, 1 /* problemAlongY */);
			// printer.writeBody(subtractionGenerator_1_ , 5 /*problemAlongX*/, 2 /*problemAlongY*/);
			printer.writeFooter();
		}
	}

	private static void all2X1Additions() {
		for (int i = 0; i < 20; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X1_, 12 /* problemAlongX */, 5 /* problemAlongY */);

			printer.writeFooter();
		}
	}

	private static void all2X1AdditionsSubtractions() {
		for (int i = 0; i < 100; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X1_, 12 /* problemAlongX */, 3 /* problemAlongY */);
			printer.writeBody(subtractionGenerator_1_, 12 /* problemAlongX */, 2/* problemAlongY */);
			printer.writeFooter();
		}
	}

	private static void allAddition2x2_3X2() {
		for (int i = 0; i < 10; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X1_, 12 /* problemAlongX */, 3 /* problemAlongY */);
			printer.writeBody(additionGenerator_3X2_, 12 /* problemAlongX */, 2 /* problemAlongY */);

			printer.writeFooter();
		}
	}

	private static void allSubtractions() {
		for (int i = 0; i < 100; i++) {
			printer.writeHeader();
			printer.writeBody(subtractionGenerator_1_, 12 /* problemAlongX */, 5 /* problemAlongY */);
			printer.writeFooter();
		}
	}

	private static void all2X1and3X1AdditionsSubtractions() {
		for (int i = 0; i < 100; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X1_, 12 /* problemAlongX */, 1 /* problemAlongY */);
			printer.writeBody(additionGenerator_3X1_, 12 /* problemAlongX */, 2/* problemAlongY */);
			printer.writeBody(subtractionGenerator_1_, 12 /* problemAlongX */, 2 /* problemAlongY */);
			printer.writeUltraThinFooter();
		}
	}

	private static void all2X2and3X2AdditionsSubtractions() {
		for (int i = 0; i < 100; i++) {
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X2_withSameNoOfDigits, 12 /* problemAlongX */, 1 /* problemAlongY */);
			printer.writeBody(additionGenerator_3X2_, 12 /* problemAlongX */, 2/* problemAlongY */);
			printer.writeBody(subtractionGenerator_1_, 12 /* problemAlongX */, 2 /* problemAlongY */);
			printer.writeUltraThinFooter();
		}
	}

	private static void allSubMulDiv() {
		for (int i = 0; i < 20; i++) {
			printer.writeHeader();
			printer.writeBody(subtractionGenerator_1_, 5/* problemAlongX */, 1/* problemAlongY */);
			printer.writeBody(multiplicationGenerator_1_tablesLessthan5, 6/* problemAlongX */, 1/* problemAlongY */);
			printer.writeHorizantalBody(divisionGenerator_2, 5/* problemAlongX */, 2/* problemAlongY */);
			printer.writeFooter();
		}
	}

	private static void allInOne() {
		for (int i = 0; i < 30; i++) {
			printer.writeHeader();

			printer.writeBody(additionGenerator_3X3_, 8/* problemAlongX */, 1/* problemAlongY */);
			printer.writeBody(subtractionGenerator_2_, 9/* problemAlongX */, 1/* problemAlongY */);
			printer.writeBody(multiplicationGenerator_2, 9/* problemAlongX */, 1/* problemAlongY */);
			printer.writeBody(additionGenerator_3X3_, 8/* problemAlongX */, 1/* problemAlongY */);

			printer.writeBody(subtractionGenerator_3_, 8/* problemAlongX */, 1/* problemAlongY */);
			printer.writeBody(multiplicationGenerator_3, 8/* problemAlongX */, 1/* problemAlongY */);

			printer.writeBody(additionGenerator_4X3_, 8/* problemAlongX */, 1/* problemAlongY */);

			printer.writeHorizantalBody(divisionGenerator_2, 6/* problemAlongX */, 1/* problemAlongY */);
			printer.writeHorizantalBody(divisionGenerator_3, 5/* problemAlongX */, 1/* problemAlongY */);
			printer.writeHorizantalBody(divisionGenerator_4, 5/* problemAlongX */, 1/* problemAlongY */);

			printer.writeFooter();
		}
	}
}
