package durs.puzzle;

import static durs.puzzle.Color.BLUE;
import static durs.puzzle.Color.GREEN;
import static durs.puzzle.Color.RED;
import static durs.puzzle.Color.YELLOW;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FourInaRow {

    private static final List<Cube> FOUR_CUBES = Arrays.asList(
            /* ----- Color top, Color front, Color bottom, Color back, Color right, Color left */
            new Cube(RED, YELLOW, YELLOW, YELLOW, BLUE, GREEN),
            new Cube(RED, BLUE, RED, GREEN, GREEN, YELLOW),
            new Cube(RED, GREEN, BLUE, YELLOW, YELLOW, BLUE),
            new Cube(RED, RED, YELLOW, BLUE, GREEN, BLUE));

    /**
     * Max number of permutations : one cube takes 4 (x-axis) * 4 (y-axis) * 4 (z-axis) = 64 (2^6) number of
     * permutations 4 cubes takes 2^6 * 2^6 * 2^6 * 2^6 = 2^24 number of permutations = 16,777,216 max permutations
     * 
     * @return
     */
    List<List<Cube>> solve() {
        List<Cube> cubes = new ArrayList<>();
        List<List<Cube>> solutions = new ArrayList<>();

        solve(0, cubes, solutions);
        return solutions;
    }

    private void solve(int currentPosition, List<Cube> cubes, List<List<Cube>> solutions) {
        Cube currentCube = new Cube(FOUR_CUBES.get(currentPosition));
        cubes.add(currentCube);

        boolean canRotateMore = true;
        while (canRotateMore) {
            if (isPromising(cubes)) {
                if (cubes.size() == 4) {
                    solutions.add(Arrays.asList(
                            new Cube(cubes.get(0)),
                            new Cube(cubes.get(1)),
                            new Cube(cubes.get(2)),
                            new Cube(cubes.get(3))));
                }
                else {
                    solve(currentPosition + 1, cubes, solutions);
                }
            }

            canRotateMore = currentCube.rotate();
        }
        cubes.remove(currentPosition);
    }

    boolean isPromising(List<Cube> cubes) {
        Set<Color> topSet = cubes.stream().map(c -> c.getTop()).collect(Collectors.toSet());
        if (topSet.size() != cubes.size()) {
            return false;
        }

        Set<Color> frontSet = cubes.stream().map(c -> c.getFront()).collect(Collectors.toSet());
        if (frontSet.size() != cubes.size()) {
            return false;
        }

        Set<Color> bottomSet = cubes.stream().map(c -> c.getBottom()).collect(Collectors.toSet());
        if (bottomSet.size() != cubes.size()) {
            return false;
        }

        Set<Color> backSet = cubes.stream().map(c -> c.getBack()).collect(Collectors.toSet());
        if (backSet.size() != cubes.size()) {
            return false;
        }

        return true;
    }

    boolean isSolved(List<Cube> cubes) {
        return cubes.size() == 4 && isPromising(cubes);
    }

    public static void main(String[] args) {
        FourInaRow fourInaRow = new FourInaRow();

        List<List<Cube>> solutions = fourInaRow.solve();

        solutions.stream()
                .forEach(solution -> {
                    System.out.println("******* Another Solution ********* ");
                    solution.stream()
                            .forEach(c -> System.out.println(c));
                });

        System.out.println("Number of Solutions : " + solutions.size());
    }

}
