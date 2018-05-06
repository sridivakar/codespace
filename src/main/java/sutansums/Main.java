package sutansums;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import sutansums.printer.PdfPrinter;
import sutansums.problem.arithmetic.add.AdditionProblems;
import sutansums.problem.arithmetic.common.ArithmeticProblem;
import sutansums.problem.arithmetic.mul.MultiplicationProblems;
import sutansums.problem.arithmetic.sub.SubtractionProblems;
import sutansums.problem.conversion.common.ConversionProblem;
import sutansums.problem.conversion.common.NullUnit;
import sutansums.problem.conversion.numbernames.NumberNameProblems;
import sutansums.worksheet.WorkSheets;

public class Main {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

	public static void main(String[] args) {
		int noOfPages = 100;
		boolean isBottomAligned = true;

		Date now = new Date();
		String filename = "target/" + dateFormat.format(now) + "_" + "worksheet";
		String txtFileExtn = ".txt";
		String pdfFileExtn = ".pdf";

		try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filename + txtFileExtn)))) {
			for (int i = 0; i < noOfPages; i++) {
				List<String> row1 = WorkSheets.concatWithBottomAligned(
						AdditionProblems.long_1x2.next(),
						AdditionProblems.long_1x2.next(),
						AdditionProblems.long_2x2.next(),
						AdditionProblems.long_2x2.next(),
						AdditionProblems.long_2x2.next(),
						AdditionProblems.long_3x2.next(),
						AdditionProblems.long_3x2.next(),
						AdditionProblems.long_3x2.next());

				List<String> row2 = WorkSheets.concatWithBottomAligned(
						AdditionProblems.long_1x3.next(),
						AdditionProblems.long_1x3.next(),
						AdditionProblems.long_2x3.next(),
						AdditionProblems.long_2x3.next(),
						AdditionProblems.long_2x3.next(),
						AdditionProblems.long_3x3.next(),
						AdditionProblems.long_3x3.next(),
						AdditionProblems.long_3x3.next());

				List<String> row3 = WorkSheets.concatWithBottomAligned(
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_2x2_withOutBorrow.next(),
						SubtractionProblems.long_2x2_withOutBorrow.next(),
						SubtractionProblems.long_2x2_withOutBorrow.next(),
						SubtractionProblems.long_2x2_withOutBorrow.next(),
						SubtractionProblems.long_2x2_withOutBorrow.next());

				List<String> row4 = WorkSheets.concatWithBottomAligned(
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next(),
						MultiplicationProblems.long_1x2.next());

				WorkSheets.getHeader(WorkSheets.A5_COLUMNS, WorkSheets.A5_HEADER_LINES).stream()
						.forEach(s -> writer.println(s));
				writer.println();
				row1.stream().forEach(s -> writer.println(s));
				writer.println();
				row2.stream().forEach(s -> writer.println(s));
				writer.println();
				row3.stream().forEach(s -> writer.println(s));
				writer.println();
				row4.stream().forEach(s -> writer.println(s));
				writer.println();
				WorkSheets.getFooter(WorkSheets.A5_COLUMNS, WorkSheets.A5_FOOTER_LINES).stream()
						.forEach(s -> writer.println(s));

				List<String> convRow1 = WorkSheets.getOneLiners(WorkSheets.A5_COLUMNS,
						NumberNameProblems.name_2.next(),
						NumberNameProblems.name_2.next(),
						NumberNameProblems.name_2.next(),
						NumberNameProblems.name_3.next(),
						NumberNameProblems.name_3.next(),
						NumberNameProblems.name_3.next(),

						NumberNameProblems.long_2.next(),
						NumberNameProblems.long_2.next(),
						NumberNameProblems.long_2.next(),
						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_3.next()
				);

				WorkSheets.getHeader(WorkSheets.A5_COLUMNS, WorkSheets.A5_HEADER_LINES).stream()
						.forEach(s -> writer.println(s));

				WorkSheets.getCenteredSubHeading(WorkSheets.A5_COLUMNS, "Number Names").stream()
						.forEach(s -> writer.println(s));
				writer.println();
				convRow1.stream().forEach(s -> { writer.println(s); writer.println();});
				writer.println();
				WorkSheets.getFooter(WorkSheets.A5_COLUMNS, WorkSheets.A5_FOOTER_LINES).stream()
						.forEach(s -> writer.println(s));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PdfPrinter pdfPrinter = PdfPrinter.a5PdfPrinter();
		pdfPrinter.print(filename + txtFileExtn, filename + pdfFileExtn);
	}

	private static void dep() {

		int MAX = 10;
		Stream<ArithmeticProblem<Long>> long_1x2_add_stream = getStream(AdditionProblems.long_3x2);
		Stream<ArithmeticProblem<Long>> long_1x2_sub_stream = getStream(SubtractionProblems.long_1x2);
		Stream<ArithmeticProblem<Long>> long_1x2_mul_stream = getStream(MultiplicationProblems.long_1x2);

		Stream<ArithmeticProblem<Long>> combinedStream = long_1x2_add_stream; // Stream.concat(long_1x2_add_stream.limit(MAX),
																				// long_1x2_mul_stream.limit(100));
		Random random = new Random();
		Comparator<? super ArithmeticProblem<Long>> randomizer = (t1, t2) -> Integer.compare(random.nextInt(),
				random.nextInt());
		// combinedStream.sorted(randomizer).limit(10).map(p ->
		// p.getHorizantalString()).collect(Collectors.toList()).forEach(p ->
		// System.out.println(p));
		combinedStream.limit(10).map(p -> p.getVerticalString()).collect(Collectors.toList())
				.forEach(p -> System.out.println(p));

		Stream<ConversionProblem<Long, NullUnit>> numberNames_stream = getStream(NumberNameProblems.long_4);
		numberNames_stream.limit(10).map(p -> p.getHorizantalString()).collect(Collectors.toList())
				.forEach(p -> System.out.println(p));
	}

	private static <T> Stream<T> getStream(Iterator<T> iterator) {
		Iterable<T> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}
