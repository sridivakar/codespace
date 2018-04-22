package sutansums.generator;

public class NumberNameUtil {

	private static final String[] LESSTHAN_TWENTY = new String[] { "", "One", "Two", "Three", "Four", "Five", "Six",
			"Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
			"Seventeen", "Eighteen", "Nineteen", };

	private static final String[] TENS_FROM_TWENTY = new String[] { "", "", "Twenty", "Thirty", "Forty", "Fifty",
			"Sixty", "Seventy", "Eighty", "Ninety" };

	private NumberNameUtil() {
	}

	public static String getNumberName(int number) {
		if (number == 0) {
			return "Zero";
		}

		if (number < 20) {
			return LESSTHAN_TWENTY[number];
		}

		if (number < 100) {
			return String.valueOf(TENS_FROM_TWENTY[number / 10] + " " + LESSTHAN_TWENTY[number % 10]).trim();
		}

		if (number < 1000) {
			return String.valueOf(LESSTHAN_TWENTY[number / 100] + " Hundred" + " " + getNumberName(number % 100))
					.trim();
		}

		if (number < 100000) {
			return String.valueOf(getNumberName(number / 1000) + " Thousand" + " " + getNumberName(number % 1000))
					.trim();
		}

		if (number < 10000000) {
			return String.valueOf(getNumberName(number / 100000) + " Lakh" + " " + getNumberName(number % 100000))
					.trim();
		}

		return String.valueOf(getNumberName(number / 10000000) + " Crore" + " " + getNumberName(number % 10000000))
				.trim();
	}

}
