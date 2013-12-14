package ponderthis.p_2012_12;

/**
 * http://domino.research.ibm.com/Comm/wwwr_ponder.nsf/Challenges/December2012.html
 * 
 * December 2012
 * 
 * Ponder This Challenge:
 * 36 people live in a 6x6 grid, and each one of them lives in a separate square of the grid. Each resident's neighbors are those who live in the squares that have a common edge with that resident's square.
 * Each resident of the grid is assigned a natural number N, such that if a person receives some N>1, then he or she must also have neighbors that have been assigned all of the numbers 1,2,...,N-1.
 * Find a configuration of the 36 neighbors where the sum of their numbers is at least 90.
 * Finding a sum of 90+N will earn you N stars near your name.
 * As an example, the highest sum we can get in a 3x3 grid is 20:
 * 1 2 1
 * 4 3 4
 * 2 1 2
 *
 */
public class Solver {

	private static final int[] SEQ = { 5, 4, 3, 2, 1 };

	private static class Coordinates {
		int x;
		int y;

		Coordinates(int i, int j) {
			this.x = i;
			this.y = j;
		}
	}

	private static class CoordinatesList {
		int length;
		final Coordinates[] cells = new Coordinates[] { new Coordinates(0, 0),
				new Coordinates(0, 0), new Coordinates(0, 0),
				new Coordinates(0, 0) };

	}

	private final CoordinatesList coordinatesList = new CoordinatesList();

	public final int size;

	public final int minSum;

	private final int totalCells;

	public final int avgCellValue;

	private final int[][] a;
	private int sumSoFar = 0;
	private int solutionCount = 0;

	private int wrongPlacementCount1 = 0;
	private int wrongPlacementCount2 = 0;
	private int wrongPlacementCount3 = 0;
	private int lessSumCheck = 0;

	public Solver(int size, int minSum) {
		this.size = size;
		this.minSum = minSum;

		this.totalCells = size * size;
		this.avgCellValue = 3; // = ceil(minSum / totalCells);

		this.a = new int[size][size];
	}

	public void solve() {
		sumSoFar = 0;

		fillWithZeros();

		solve(0);

		System.out.println("Total Solutions : " + solutionCount);
		return;
	}

	private void solve(int nextIndex) {

		// Base criteria for recursion
		if (nextIndex == totalCells) {
			boolean isSumAttained = isSumAttained();
			boolean isSolved = isSumAttained && isSolved();
			if (isSolved) {
				solutionCount++;
				print();
			}
			return;
		}

		for (int k : SEQ) {
			int i = nextIndex / size;
			int j = nextIndex % size;

			a[i][j] = k;
			sumSoFar += k;

			if (isPromising(i, j)) {
				solve(nextIndex + 1);
			}

			// backtrack
			a[i][j] = 0;
			sumSoFar -= k;
		}
	}

	private boolean isPromising(int i, int j) {

		boolean isPromising = isSumPromising(i, j);

		// self
		isPromising = isPromising && isCellPromising(i, j);

		// north
		if (isPromising && (j - 1) >= 0) {
			isPromising = isCellPromising(i, j - 1);
		}

		// west
		if (isPromising && (i - 1) >= 0) {
			isPromising = isCellPromising(i - 1, j);
		}

		return isPromising;
	}

	private boolean isSumPromising(int i, int j) {
		int filledCells = (size * i + j) + 1;
		int remainingCells = totalCells - filledCells;

		// Can have a better heuristics over here?
		if ((sumSoFar + remainingCells * avgCellValue) < minSum) {
			lessSumCheck++;
			return false;
		}

		return true;
	}

	private boolean isCellPromising(int i, int j) {
		if (a[i][j] == 1) {
			return true;
		}

		CoordinatesList neighbourList = getNeighbours(i, j);
		Coordinates[] neighbouringCells = neighbourList.cells;
		if (neighbourList.length < (a[i][j] - 1)) {
			wrongPlacementCount1++;
			return false;
		}

		int numberOfNeighbouringZeros = 0;
		int expectedNeighboursBitSet = (1 << (a[i][j] - 1)) - 1;
		int actualNeighboursFoundBitSet = 0;
		
		for (int k = 0; k < neighbourList.length; k++) {
			Coordinates coordinates = neighbouringCells[k];
			if (a[coordinates.x][coordinates.y] == 0) {
				numberOfNeighbouringZeros++;
				continue;
			}
			
			int found = 1 << (a[coordinates.x][coordinates.y] - 1);
			actualNeighboursFoundBitSet = actualNeighboursFoundBitSet | found;
		}

		
		int actualRequiredNeighboursFoundBitSet = (actualNeighboursFoundBitSet & expectedNeighboursBitSet);
		if (numberOfNeighbouringZeros == 0) {
			if (actualRequiredNeighboursFoundBitSet != expectedNeighboursBitSet) {
				wrongPlacementCount2++;
				return false;
			} else {
				// no-op, good to go
			}
		} else {
			int expectedNeighboursCount = numberOfBitsSet(expectedNeighboursBitSet);
			int actualRequiredNeighboursFoundCount = numberOfBitsSet(actualRequiredNeighboursFoundBitSet);
			if (actualRequiredNeighboursFoundCount + numberOfNeighbouringZeros < expectedNeighboursCount) {
				wrongPlacementCount3++;
				return false;
			} else {
				// no-op, good to go
			}
		}

		return true;
	}

	private boolean isSolved() {
		boolean isSolved = true;

		outer: for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 0) {
					isSolved = false;
					assert(false);
					break outer;
				}
				if (a[i][j] == 1) {
					continue;
				}

				CoordinatesList neighbourList = getNeighbours(i, j);
				Coordinates[] neighbours = neighbourList.cells;
				if (neighbourList.length < (a[i][j] - 1)) {
					isSolved = false;
					assert(false);
					break outer;
				}

				int expectedNeighboursBitSet = (1 << (a[i][j] - 1)) - 1;
				int actualNeighboursFoundBitSet = 0;
				for (int k = 0; k < neighbourList.length; k++) {
					Coordinates coordinates = neighbours[k];
					int found = 1 << (a[coordinates.x][coordinates.y] - 1);
					actualNeighboursFoundBitSet = actualNeighboursFoundBitSet | found;
				}

				if ((actualNeighboursFoundBitSet & expectedNeighboursBitSet) != expectedNeighboursBitSet) {
					isSolved = false;
					assert(false);
					break outer;
				}
			}
		}

		return isSolved;
	}

	private void fillWithZeros() {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = 0;
			}
		}
	}

	private boolean isSumAttained() {
		if (sumSoFar >= minSum) {
			return true;
		} else {
			return false;
		}
	}

	private void print() {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}

		System.out.println("Sum : " + sumSoFar);
		System.out.println("------------");
	}

	private int numberOfBitsSet(int n) {
		int total = 0;

		for (int i = 0; i < SEQ.length; i++) {
			int x = 1 << i;
			if ((n & x) != 0) {
				total++;
			}
		}

		return total;
	}

	private CoordinatesList getNeighbours(int i, int j) {
		int k = 0;

		// north
		if (i > 0) {
			coordinatesList.cells[k].x = i - 1;
			coordinatesList.cells[k++].y = j;
		}

		// east
		if (j < size - 1) {
			coordinatesList.cells[k].x = i;
			coordinatesList.cells[k++].y = j + 1;
		}

		// west
		if (j > 0) {
			coordinatesList.cells[k].x = i;
			coordinatesList.cells[k++].y = j - 1;
		}

		// south
		if (i < size - 1) {
			coordinatesList.cells[k].x = i + 1;
			coordinatesList.cells[k++].y = j;
		}

		coordinatesList.length = k;
		return coordinatesList;
	}

	public static void main(String[] args) {
		long start = System.nanoTime();
		Solver solver = new Solver(6, 93);
		solver.solve();
		long end = System.nanoTime();
		System.out.println("Time Taken: " + (end - start) / 1000000 + " millis");
		System.out.println("wrongPlacementCount1: " + solver.wrongPlacementCount1
				+ " wrongPlacementCount2: " + solver.wrongPlacementCount2 
				+ " wrongPlacementCount3: " + solver.wrongPlacementCount3
				+ " lessSumCheck: " + solver.lessSumCheck);

	}

}
