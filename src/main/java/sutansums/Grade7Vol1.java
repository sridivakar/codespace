package sutansums;

import sutansums.printer.PdfPrinter;
import sutansums.problem.arithmetic.common.ArithmeticProblemIterator;
import sutansums.problem.arithmetic.mul.MultiplicationProblems;
import sutansums.worksheet.WorkSheets;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Grade7Vol1 {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

	public static void main(String[] args) {
		int noOfPages = 19;

		Date now = new Date();
		String filename = "target/" + dateFormat.format(now) + "_" + "worksheet";
		String txtFileExtn = ".txt";
		String pdfFileExtn = ".pdf";
		int rowsPerPage = 4;

		try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filename + txtFileExtn)))) {
			for (int i = 0; i < noOfPages; i++) {
				writeMultiplicationProblemsOfTable(writer, i, rowsPerPage);
				writeMultiplicationProblemsOfTable(writer, i, rowsPerPage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PdfPrinter pdfPrinter = PdfPrinter.a5PdfPrinter();
		pdfPrinter.print(filename + txtFileExtn, filename + pdfFileExtn);
	}

	private static void writeMultiplicationProblemsOfTable(PrintWriter writer, int table, int rowsPerPage) {
		WorkSheets.getThinHeader(WorkSheets.A5_COLUMNS, WorkSheets.A5_HEADER_LINES)
				.stream()
				.forEach(s -> writer.println(s));
		writer.println();
		ArithmeticProblemIterator<Long> iterator = MultiplicationProblems.getForTable(table + 2);
		for (int j = 0; j < rowsPerPage; j++) {
			List<String> row = WorkSheets.concatWithBottomAligned(
					iterator.next(),
					iterator.next(),
					iterator.next(),
					iterator.next(),
					iterator.next(),
					iterator.next()
			);
			row.stream().forEach(s -> writer.println(s));
			writer.println();
		}
		WorkSheets.getThinFooter(WorkSheets.A5_COLUMNS, WorkSheets.A5_FOOTER_LINES)
				.stream()
				.forEach(s -> writer.println(s));
		writer.println(PdfPrinter.NEW_PAGE);
	}

}
