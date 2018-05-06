package sutansums.problem.generator;

import java.util.List;
import java.util.Random;

public interface IGenerator<T> {

	Random random = new Random();

	List<T> getNext();
}
