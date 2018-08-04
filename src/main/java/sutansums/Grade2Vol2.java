package sutansums;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sutansums.printer.PdfPrinter;
import sutansums.problem.arithmetic.add.AdditionProblems;
import sutansums.problem.arithmetic.mul.MultiplicationProblems;
import sutansums.problem.arithmetic.sub.SubtractionProblems;
import sutansums.problem.conversion.numbernames.NumberNameProblems;
import sutansums.worksheet.WorkSheets;

public class Grade2Vol2 {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

	public static void main(String[] args) {
		int noOfPages = 100;

		Date now = new Date();
		String filename = "target/" + dateFormat.format(now) + "_" + "worksheet";
		String txtFileExtn = ".txt";
		String pdfFileExtn = ".pdf";

		try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filename + txtFileExtn)))) {
			for (int i = 0; i < noOfPages; i++) {
				List<String> row1 = WorkSheets.concatWithBottomAligned(
						AdditionProblems.long_1x2.next(),
						AdditionProblems.long_1x3.next(),
						AdditionProblems.long_2x2.next(),
						AdditionProblems.long_2x2.next(),
						AdditionProblems.long_2x3.next(),
						AdditionProblems.long_3x2.next(),
						AdditionProblems.long_3x2.next(),
						AdditionProblems.long_3x3.next());

				List<String> row2 = WorkSheets.concatWithBottomAligned(
						AdditionProblems.long_1x3.next(),
						AdditionProblems.long_1x4.next(),
						AdditionProblems.long_2x3.next(),
						AdditionProblems.long_2x3.next(),
						AdditionProblems.long_2x4.next(),
						AdditionProblems.long_3x3.next(),
						AdditionProblems.long_3x4.next(),
						AdditionProblems.long_3x4.next());

				List<String> row3 = WorkSheets.concatWithBottomAligned(
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_1x2.next(),
						SubtractionProblems.long_2x2_withBorrow.next(),
						SubtractionProblems.long_2x2_withBorrow.next(),
						SubtractionProblems.long_2x2_withBorrow.next(),
						SubtractionProblems.long_2x2_withBorrow.next(),
						SubtractionProblems.long_2x2_withBorrow.next(),
						SubtractionProblems.long_2x2_withBorrow.next());
				
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

				WorkSheets.getThinHeader(WorkSheets.A5_COLUMNS, WorkSheets.A5_HEADER_LINES)
						.stream()
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
				//				row5.stream().forEach(s -> writer.println(s));
				//				writer.println();
				WorkSheets.getThinFooter(WorkSheets.A5_COLUMNS, WorkSheets.A5_FOOTER_LINES)
						.stream()
						.forEach(s -> writer.println(s));

				List<String> convPara = WorkSheets.getOneLiners(WorkSheets.A5_COLUMNS,
						NumberNameProblems.name_3.next(),
						NumberNameProblems.name_3.next(),
						NumberNameProblems.name_4.next(),
						NumberNameProblems.name_4.next(),

						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_3.next(),
						NumberNameProblems.long_4.next(),
						NumberNameProblems.long_4.next());

				WorkSheets.getHeader(WorkSheets.A5_COLUMNS, WorkSheets.A5_HEADER_LINES)
						.stream()
						.forEach(s -> writer.println(s));

				WorkSheets.getCenteredSubHeading(WorkSheets.A5_COLUMNS, "Number Names")
						.stream()
						.forEach(s -> writer.println(s));
				writer.println();
				convPara.stream().forEach(s -> {
					writer.println(s);
					writer.println();
				});
				
				WorkSheets.getFooter(WorkSheets.A5_COLUMNS, WorkSheets.A5_FOOTER_LINES)
						.stream()
						.forEach(s -> writer.println(s));
				writer.println(PdfPrinter.NEW_PAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PdfPrinter pdfPrinter = PdfPrinter.a5PdfPrinter();
		pdfPrinter.print(filename + txtFileExtn, filename + pdfFileExtn);
	}

}
