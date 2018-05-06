package sutansums.problem.generator;

import java.util.List;

public abstract class AbstractTransformer<S, T> implements IGenerator<T> {

	protected final IGenerator<S> generator;

	public AbstractTransformer(IGenerator<S> generator) {
		this.generator = generator;
	}

	@Override
	public List<T> getNext() {
		return transform(generator.getNext());
	}

	public abstract List<T> transform(List<S> list);

}
