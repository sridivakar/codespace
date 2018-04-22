package sutansums.problem;

import java.util.Comparator;

public class DescendingOrder implements Comparator<Integer> {

	/*
	 * Returns a negative integer if o1 > o2 a zero if o1 == o2 a positive integer if o1 < o2
	 */
	public int compare(Integer o1, Integer o2) {
		if (o1.intValue() == o2.intValue()) {
			return 0;
		} else if (o1.intValue() < o2.intValue()) {
			return 1;
		} else {
			return -1;
		}
	}

}
