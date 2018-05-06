package sutansums.worksheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sutansums.problem.IProblem;
import sutansums.problem.arithmetic.common.ArithmeticProblem;

public class WorkSheets {

	/**
	 * <pre>
	 *  
	 * Notepad 
	 *   Page Size : Letter 
	 *   Page set up : Left = 1"; Right = 1"; Top = 0.75"; Bottom = 0.75" 
	 *   Font : Courier New + Bold, Size 16 
	 *   COLUMNS = 46, ROWS = 42
	 * </pre>
	 */

	public static final int A4_COLUMNS = 46;
	public static final String[] A4_HEADER_LINES = new String[] {
			// ...1234567890123456789012345678901234567890123456789012345678901234567890
			/**/"Name :                       Date :           ",
			/**/"Start Time :             End Time :           ",
	};

	public static final String[] A4_FOOTER_LINES = new String[] {
			// ..1234567890123456789012345678901234567890123456789012345678901234567890
			/**/"Corrects:        Wrongs:        Sign:         "
	};

	/**
	 * <pre>
	 * 
	 * Notepad 
	 *  Page Size : A5
	 *  Page set up : Left = 0.5"; Right = 0.5"; Top = 0.5"; Bottom = 0.5"
	 *  Font : Courier New + Bold, Size 14
	 *  COLUMNS = 30, ROWS = 27
	 * </pre>
	 */
	public static final int A5_ROWS = 32;
	public static final int A5_COLUMNS = 41;
	public static final String[] A5_HEADER_LINES = new String[] {
			// ..12345678901234567890123456789012345678901
			/**/"Date:       Start Time:      End Time:",
	};

	public static final String[] A5_FOOTER_LINES = new String[] {
			// ..12345678901234567890123456789012345678901
			/**/"Corrects:     Wrongs:     Sign:          "
	};

	private static final String GAP_H = "  ";

	public static String getLine(int columns) {
		StringBuilder builder = new StringBuilder();
		builder.setLength(0);

		for (int i = 0; i < columns; i++) {
			builder.append("_");
		}

		return builder.toString();
	}

	public static List<String> getCenteredSubHeading(int columns, String headerLine) {
		char[] spaces = new char[(columns - headerLine.length()) / 2];
		Arrays.fill(spaces, ' ');

		char[] underscore = new char[headerLine.length()];
		Arrays.fill(underscore, '_');

		String subHeading = String.valueOf(spaces) + headerLine + String.valueOf(spaces);
		String underLine = String.valueOf(spaces) + String.valueOf(underscore) + String.valueOf(spaces);

		return Arrays.asList(subHeading, underLine);
	}

	public static List<String> getOneLiners(int columns, IProblem... problems) {
		List<String> result = new ArrayList<>(problems.length);

		for (int i = 1; i < problems.length; i++) {
			char[] blank = new char[columns - problems[i].getHorizantalString().length()];
			Arrays.fill(blank, '_');
			String blankLine = String.valueOf(blank);
			result.add(problems[i].getHorizantalString() + blankLine);
		}
		return result;
	}

	public static List<String> getHeader(int columns, String[] headerLines) {
		List<String> header = new ArrayList<>();

		header.add(getLine(columns));
		for (String headerLine : headerLines) {
			header.add(headerLine);
		}
		header.add(getLine(columns));

		return header;
	}

	public static List<String> getThinHeader(int columns, String[] headerLines) {
		List<String> header = new ArrayList<>();
		for (String headerLine : headerLines) {
			header.add(headerLine);
		}
		header.add(getLine(columns));

		return header;
	}

	public static List<String> getUltraThinHeader(String[] headerLines) {
		List<String> header = new ArrayList<>();
		for (String headerLine : headerLines) {
			header.add(headerLine);
		}

		return header;
	}

	public static List<String> getFooter(int columns, String[] footerLines) {
		List<String> footer = new ArrayList<>();

		footer.add(getLine(columns));
		for (String footerLine : footerLines) {
			footer.add(footerLine);
		}
		footer.add(getLine(columns));

		return footer;
	}

	public static List<String> getThinFooter(int columns, String[] footerLines) {
		List<String> footer = new ArrayList<>();

		footer.add(getLine(columns));
		for (String footerLine : footerLines) {
			footer.add(footerLine);
		}
		return footer;
	}

	public static List<String> getUltraThinFooter(String[] footerLines) {
		List<String> footer = new ArrayList<>();

		for (String footerLine : footerLines) {
			footer.add(footerLine);
		}
		return footer;
	}

	public static List<String> concatWithBottomAligned(ArithmeticProblem<Long>... problems) {
		List<String> result = new ArrayList<>(problems[0].getVerticalString());

		for (int i = 1; i < problems.length; i++) {
			List<String> problem = problems[i].getVerticalString();
			result = concatProblems(true /* isBottomAligned */, result, problem);
		}
		return result;
	}

	public static List<String> concatProblems(boolean isBottomAligned, List<String>... problems) {
		List<String> result = new ArrayList<>(problems[0]);

		for (int i = 1; i < problems.length; i++) {
			List<String> problem = problems[i];
			result = concatProblems(isBottomAligned, result, problem);
		}
		return result;
	}

	public static List<String> concatProblems(boolean isBottomAligned, List<String> problemOne,
			List<String> problemTwo) {
		int problemOneHeight = problemOne.size();
		int problemTwoHeight = problemTwo.size();

		List<String> result = new ArrayList<>();
		if (problemOneHeight < problemTwoHeight) {
			List<String> alignedProblemOne = alignProblem(isBottomAligned, problemTwoHeight, problemOne);

			for (int i = 0; i < problemTwoHeight; i++) {
				result.add(alignedProblemOne.get(i) + GAP_H + problemTwo.get(i));
			}
		} else {
			List<String> alignedProblemTwo = alignProblem(isBottomAligned, problemOneHeight, problemTwo);

			for (int i = 0; i < problemOneHeight; i++) {
				result.add(problemOne.get(i) + GAP_H + alignedProblemTwo.get(i));
			}
		}

		return result;

	}

	private static List<String> alignProblem(boolean isBottomAligned, int targetHeight, List<String> problem) {
		int problemHeight = problem.size();
		Integer problemWidth = problem
				.stream()
				.map(n -> Integer.valueOf(String.valueOf(n).length()))
				.max(java.util.Comparator.naturalOrder())
				.get();

		char[] problemSpaces = new char[problemWidth];
		Arrays.fill(problemSpaces, ' ');
		List<String> alignedProblemOne = new ArrayList<>();

		if (isBottomAligned) {
			for (int i = 0; i < targetHeight - problemHeight; i++) {
				alignedProblemOne.add(String.valueOf(problemSpaces));
			}
		}

		alignedProblemOne.addAll(problem);

		if (!isBottomAligned) {
			for (int i = 0; i < targetHeight - problemHeight; i++) {
				alignedProblemOne.add(String.valueOf(problemSpaces));
			}
		}
		return alignedProblemOne;
	}
}
