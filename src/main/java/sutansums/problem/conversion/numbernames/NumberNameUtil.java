package sutansums.problem.conversion.numbernames;

public class NumberNameUtil {

	private static final String[] LESSTHAN_TWENTY = new String[] { "", "One", "Two", "Three", "Four", "Five", "Six",
			"Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
			"Seventeen", "Eighteen", "Nineteen", };

	private static final String[] TENS_FROM_TWENTY = new String[] { "", "", "Twenty", "Thirty", "Forty", "Fifty",
			"Sixty", "Seventy", "Eighty", "Ninety" };

	private NumberNameUtil() {
	}

	public static String getIndianNumberName(long number) {
		if (number == 0) {
			return "Zero";
		}

		if (number < 20) {
			return LESSTHAN_TWENTY[(int) number];
		}

		if (number < 100) {
			return String.valueOf(TENS_FROM_TWENTY[(int) number / 10] + " " + LESSTHAN_TWENTY[(int) number % 10])
					.trim();
		}

		if (number < 1000) {
			String tens = getIndianNumberName(number % 100);
			return String
					.valueOf(LESSTHAN_TWENTY[(int) number / 100] + " Hundred" + " " + ((tens.trim().length() == 0) ? "" : "and " + tens))
					.trim();
		}

		if (number < 100000) {
			return String
					.valueOf(
							getIndianNumberName(number / 1000) + " Thousand" + " " + getIndianNumberName(number % 1000))
					.trim();
		}

		if (number < 10000000) {
			return String
					.valueOf(
							getIndianNumberName(number / 100000) + " Lakh" + " " + getIndianNumberName(number % 100000))
					.trim();
		}

		return String
				.valueOf(getIndianNumberName(number / 10000000) + " Crore" + " "
						+ getIndianNumberName(number % 10000000))
				.trim();
	}

}
