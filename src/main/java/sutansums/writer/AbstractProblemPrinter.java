package sutansums.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import sutansums.generator.IGenerator;
import sutansums.generator.NumberNamesGenerator;
import sutansums.generator.NumberNumeralsGenerator;
import sutansums.printer.PdfPrinter;
import sutansums.problem.IProblem;

public abstract class AbstractProblemPrinter {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	private static final String GAP_H = "  ";
	private static final int GAP_V = 1;

	private static final String H_GAP_H = "    ";
	private static final int H_GAP_V = 8;

	private final String filename;
	private final String fileExtn;
	private final String linePrefix;
	private final String lineSuffix;

	private final List<String> header;
	private final List<String> thinHeader;
	private final List<String> ultraThinHeader;

	private final List<String> footer;
	private final List<String> thinFooter;
	private final List<String> ultraThinFooter;
	private final String gapString;
	protected PrintWriter writer;

	private PdfPrinter pdfPrinter;

	public AbstractProblemPrinter(boolean isMarkDown, PdfPrinter pdfPrinter) {

		this.pdfPrinter = pdfPrinter;
		// TODO include timestamp in file name
		Date now = new Date();
		this.filename = "target/" + dateFormat.format(now) + "_" + "worksheet";
		String fExtn = ".txt";
		String lprefix = "";
		String lsuffix = "";
		if (isMarkDown) {
			fExtn = ".md";
			lprefix = lsuffix = "```";
		}
		this.fileExtn = fExtn;
		this.linePrefix = lprefix;
		this.lineSuffix = lsuffix;

		this.header = prepareHeader();
		this.thinHeader = prepareThinHeader();
		this.ultraThinHeader = prepareUltraThinHeader();

		this.footer = prepareFooter();
		this.thinFooter = prepareThinFooter();
		this.ultraThinFooter = prepareUltraThinFooter();
		this.gapString = "    ";
		try {
			this.writer = new PrintWriter(new FileWriter(new File(filename + fileExtn)));
		} catch (IOException e) {
			this.writer = null;
			e.printStackTrace();
		}
	}

	public void writeBody(IGenerator<?> generator, int problemAlongX, int problemAlongY) {
		StringBuilder builder = new StringBuilder();

		// Prepare a dash line depending up on number of digits
		builder.setLength(0);
		for (int k = 0; k < generator.getNumberOfDigits() + 1; k++) {
			builder.append("-");
		}
		String dashLine = builder.toString();

		// Prepare a big dash line for all problems in a row
		builder.setLength(0);
		for (int k = 0; k < problemAlongX; k++) {
			builder.append(dashLine);
			if (k != problemAlongX - 1) {
				builder.append(GAP_H);
			}
		}
		String resultLine = builder.toString();
		for (int i = 0; i < problemAlongY; i++) {
			IProblem[] problemList = new IProblem[problemAlongX];

			// Prepare all problems that fit in a row
			for (int j = 0; j < problemAlongX; j++) {
				problemList[j] = generator.getNext();
			}

			// Print all problems that fit in a row
			for (int k = 0; k < generator.getNumberOfOperands(); k++) {
				builder.setLength(0);
				for (int j = 0; j < problemList.length; j++) {
					// put either a space or symbol for every line
					if (k == generator.getNumberOfOperands() - 1) {
						builder.append(problemList[j].getSymbol());
					} else {
						builder.append(" ");
					}

					// format number to have leading spaces if number has less digits
					String number = String.valueOf(problemList[j].getOperands()[k]);
					char[] spaces = new char[generator.getNumberOfDigits() - number.length()];
					Arrays.fill(spaces, ' ');
					number = new String(spaces) + number;
					builder.append(number);

					// append gap if not last problem in the row
					if (j != problemList.length - 1) {
						builder.append(GAP_H);
					}
				}

				writer.println(builder.toString());
			}

			// print lines for writing result

			writer.println(resultLine);
			writer.println("  ");
			writer.println(resultLine);
			// print lines for vertical gap between problems
			for (int k = 0; k < GAP_V; k++) {
				writer.println("  ");
			}
		}
	}

	public void writeHorizantalBody(IGenerator<?> generator, int problemAlongX, int problemAlongY) {
		StringBuilder builder = new StringBuilder();

		// Prepare a dash line depending up on number of digits
		builder.setLength(0);
		builder.append(linePrefix);
		for (int k = 0; k < generator.getNumberOfDigits() + 2; k++) {
			builder.append("_");
		}
		String dashLine = builder.append(lineSuffix).toString();

		// Prepare a big dash line for all problems in a row
		builder.setLength(0);
		builder.append(linePrefix);
		for (int k = 0; k < problemAlongX; k++) {
			builder.append(dashLine);
			if (k != problemAlongX - 1) {
				builder.append(H_GAP_H);
			}
		}
		String resultLine = builder.append(lineSuffix).toString();

		// writer.println("  " );
		for (int i = 0; i < problemAlongY; i++) {
			IProblem[] problemList = new IProblem[problemAlongX];

			// Prepare all problems that fit in a row
			for (int j = 0; j < problemAlongX; j++) {
				problemList[j] = generator.getNext();
			}

			writer.println(resultLine);
			builder.setLength(0);
			builder.append(linePrefix);
			// Print all problems that fit in a row
			for (int j = 0; j < problemList.length; j++) {
				String number = String.valueOf(problemList[j].getOperands()[0]);
				builder.append(number);
				builder.append(problemList[j].getSymbol());
				builder.append(problemList[j].getOperands()[1]);

				// append gap if not last problem in the row
				if (j != problemList.length - 1) {
					builder.append(H_GAP_H);
				}

			}
			writer.println(builder.append(lineSuffix).toString());

			// print lines for vertical gap between problems
			for (int k = 0; k < H_GAP_V; k++) {
				writer.println("  ");
			}
		}
	}

	public void writeSingleLineProblems(NumberNamesGenerator generator, int problemAlongY, int extraResultLines) {
		for (int i = 0; i < problemAlongY; i++) {
			Integer next = generator.getNext().getOperands()[0];
			String number = String.valueOf(next);
			String seperator = ": ";

			// format number to have leading spaces if number has less digits
			char[] spaces = new char[generator.getNumberOfDigits() - number.length()];
			Arrays.fill(spaces, ' ');
			String paddedNumber = new String(spaces) + number;

			// Prepare a dash line depending up on number of digits
			char[] dashes = new char[getColumns() - (paddedNumber.length() % getColumns()) - seperator.length()];
			Arrays.fill(dashes, '_');
			String dashLine = new String(dashes);

			char[] fullDashes = new char[getColumns()];
			Arrays.fill(fullDashes, '_');
			String fullDashLine = new String(fullDashes);

			String question = paddedNumber + seperator + dashLine;
			writer.println(question);

			for (int j = 0; j < extraResultLines; j++) {
				writer.println(fullDashLine);
			}
			writer.println("  ");
		}

		// print lines for vertical gap between problems
		for (int k = 0; k < GAP_V; k++) {
			writer.println("  ");
		}
	}

	public void writeSingleLineProblems(NumberNumeralsGenerator generator, int problemAlongY) {
		for (int i = 0; i < problemAlongY; i++) {
			String numberName = generator.getNext().getOperands()[0];
			String seperator = ": ";

			// Prepare a dash line depending up on number of digits
			char[] dashes = new char[20];
			Arrays.fill(dashes, '_');
			String dashLine = new String(dashes);

			String question = numberName + seperator + dashLine;
			writer.println(question);
			writer.println("  ");
		}

		// print lines for vertical gap between problems
		for (int k = 0; k < GAP_V; k++) {
			writer.println("  ");
		}
	}

	public void writeHeader() {
		for (String line : header) {
			writer.println(line);
		}
	}

	public void writeThinHeader() {
		for (String line : thinHeader) {
			writer.println(line);
		}
	}

	public void writeUltraThinHeader() {
		for (String line : ultraThinHeader) {
			writer.println(line);
		}
	}

	public void writeFooter() {
		for (String line : footer) {
			writer.println(line);
		}
	}

	public void writeThinFooter() {
		for (String line : thinFooter) {
			writer.println(line);
		}
	}

	public void writeUltraThinFooter() {
		for (String line : ultraThinFooter) {
			writer.println(line);
		}
	}

	protected String getLine() {
		StringBuilder builder = new StringBuilder();
		builder.setLength(0);

		for (int i = 0; i < getColumns(); i++) {
			builder.append("-");
		}

		return builder.toString();
	}

	protected abstract int getColumns();

	protected List<String> prepareHeader() {
		String[] headerLines = getHeaderLines();

		List<String> header = new ArrayList<>();

		header.add(getLine());
		for (String headerLine : headerLines) {
			header.add(linePrefix + headerLine + lineSuffix);
		}
		header.add(getLine());
		header.add(linePrefix);

		return header;
	}

	protected List<String> prepareThinHeader() {
		String[] headerLines = getHeaderLines();

		List<String> header = new ArrayList<>();
		for (String headerLine : headerLines) {
			header.add(linePrefix + headerLine + lineSuffix);
		}
		header.add(getLine());
		header.add(linePrefix);

		return header;
	}

	protected List<String> prepareUltraThinHeader() {
		String[] headerLines = getHeaderLines();

		List<String> header = new ArrayList<>();
		for (String headerLine : headerLines) {
			header.add(linePrefix + headerLine + lineSuffix);
		}
		header.add(linePrefix);

		return header;
	}

	protected abstract String[] getHeaderLines();

	protected List<String> prepareFooter() {
		String[] footerLines = getFooterLines();

		List<String> footer = new ArrayList<>();

		footer.add(lineSuffix + getLine());
		for (String footerLine : footerLines) {
			footer.add(linePrefix + footerLine + lineSuffix);
		}
		footer.add(getLine());

		return footer;
	}

	protected List<String> prepareThinFooter() {
		String[] footerLines = getFooterLines();

		List<String> footer = new ArrayList<>();

		footer.add(lineSuffix + getLine());
		for (String footerLine : footerLines) {
			footer.add(linePrefix + footerLine + lineSuffix);
		}
		return footer;
	}

	protected List<String> prepareUltraThinFooter() {
		String[] footerLines = getFooterLines();

		List<String> footer = new ArrayList<>();

		for (String footerLine : footerLines) {
			footer.add(footerLine + lineSuffix);
		}
		return footer;
	}

	protected abstract String[] getFooterLines();

	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void createPdf() {
		pdfPrinter.print(filename + fileExtn, filename + ".pdf");
	}

}
