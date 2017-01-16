package sutansums.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import sutansums.generator.IGenerator;
import sutansums.problem.IProblem;

public class ProblemPrinter {

	/*
	 * Notepad 
	 *  Page Size : Letter
	 * 	Page set up : Left = 1"; Right = 1"; Top = 0.75"; Bottom = 0.75"
	 *  Font : Courier New + Bold, Size 16
	 *  COLUMNS = 46, ROWS = 42
	 */
	private static final int COLUMNS = 46;
	private static final int ROWS = 42;

	private static final String GAP_H = "  ";
	private static final int GAP_V = 1;

	private static final String H_GAP_H = "    ";
	private static final int H_GAP_V = 8;
	
	private final String filename;
	private final String[] header;
	private final String[] footer;
	private final String gapString;
	private PrintWriter writer;

	public ProblemPrinter() {
		// TODO include timestamp in file name
		filename = "test.txt";
		this.header = prepareHeader();
		this.footer = prepareFooter();
		this.gapString = "    ";
		try {
			this.writer = new PrintWriter(new FileWriter(new File(filename)));
		} catch (IOException e) {
			this.writer = null;
			e.printStackTrace();
		}
	}

	private String[] prepareHeader() {
		String[] header = new String[5];

		header[0] = getLine();
		/*1234567890123456789012345678901234567890123456789012345678901234567890*/
		header[1] = "Name :                       Date :           ";
		header[2] = "Start Time :             End Time :           ";

		header[3] = getLine();
		header[4] = "";

		return header;
	}

	private String getLine() {
		StringBuilder builder = new StringBuilder();
		builder.setLength(0);

		for (int i = 0; i < COLUMNS; i++) {
			builder.append("-");
		}

		return builder.toString();
	}

	private String[] prepareFooter() {
		String[] footer = new String[3];

		footer[0] = getLine();
		//1234567890123456789012345678901234567890123456789012345678901234567890
		footer[1] = "Corrects:        Wrongs:        Sign:         ";
		footer[2] = getLine();

		return footer;
	}

	public void write(IGenerator<?> generator, int pages, int problemAlongX, int problemAlongY) {
		try {
			for (int i = 0; i < pages; i++) {
				writeHeader();
				
				writeBody(generator, problemAlongX, problemAlongY);
				
				writeFooter();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void writeBody(IGenerator<?> generator, int problemAlongX, int problemAlongY) {		
		StringBuilder builder = new StringBuilder();

		// Prepare a dash line depending up on number of digits
		builder.setLength(0);
		for (int k = 0; k < generator.getNumberOfDigits()+1; k++) {				
			builder.append("-");	
		}
		String dashLine = builder.toString();

		// Prepare a big dash line for all problems in a row 
		builder.setLength(0);		
		for (int k = 0; k < problemAlongX; k++) {				
			builder.append(dashLine);	
			if (k != problemAlongX -1) {
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
			for(int k = 0; k < generator.getNumberOfOperands(); k++) {
				builder.setLength(0);
				for (int j = 0; j < problemList.length; j++) {
					// put either a space or symbol for every line
					if (k == generator.getNumberOfOperands() -1) {
						builder.append(generator.getSymbol());
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
					if (j != problemList.length -1) {
						builder.append(GAP_H);
					}
				}
				
				writer.println(builder.toString());				
			}

			// print lines for writing result
			writer.println(resultLine);
			writer.println();
			writer.println(resultLine);
			
			// print lines for vertical gap between problems
			for(int k = 0; k < GAP_V; k++) {
				writer.println();
			}
		}		
	}

	
	public void writeHorizantal(IGenerator<?> generator, int pages, int problemAlongX, int problemAlongY) {
		try {
			for (int i = 0; i < pages; i++) {
				writeHeader();
				
				writeHorizantalBody(generator, problemAlongX, problemAlongY);
				
				writeFooter();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void writeHorizantalBody(IGenerator<?> generator, int problemAlongX, int problemAlongY) {		
		StringBuilder builder = new StringBuilder();

		// Prepare a dash line depending up on number of digits
		builder.setLength(0);
		for (int k = 0; k < generator.getNumberOfDigits()+2; k++) {				
			builder.append("_");	
		}
		String dashLine = builder.toString();

		// Prepare a big dash line for all problems in a row 
		builder.setLength(0);		
		for (int k = 0; k < problemAlongX; k++) {				
			builder.append(dashLine);	
			if (k != problemAlongX -1) {
				builder.append(H_GAP_H);
			}
		}		
		String resultLine = builder.toString();	
		
		//writer.println();
		for (int i = 0; i < problemAlongY; i++) {
			IProblem[] problemList = new IProblem[problemAlongX];

			// Prepare all problems that fit in a row
			for (int j = 0; j < problemAlongX; j++) {				
				problemList[j] = generator.getNext();						
			}

			writer.println(resultLine);
			builder.setLength(0);
			// Print all problems that fit in a row
			for (int j = 0; j < problemList.length; j++) {							
				String number = String.valueOf(problemList[j].getOperands()[0]);									
				builder.append(number);
				builder.append(generator.getSymbol());
				builder.append(problemList[j].getOperands()[1]);

				// append gap if not last problem in the row
				if (j != problemList.length -1) {
					builder.append(H_GAP_H);
				}
				
			}
			writer.println(builder.toString());

			// print lines for vertical gap between problems
			for(int k = 0; k < H_GAP_V; k++) {
				writer.println();
			}
		}		
	}
	
	public void writeFooter() {
		for (String line : footer) {
			writer.println(line);
		}
	}

	public void writeHeader() {
		for (String line : header) {
			writer.println(line);
		}
	}

	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
