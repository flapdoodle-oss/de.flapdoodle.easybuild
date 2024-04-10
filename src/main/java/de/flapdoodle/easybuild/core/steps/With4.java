package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Set;
import java.util.function.Function;

public abstract class With4<A, B, C, D, T> implements BuildStep<T> {
    private final ArtefactId<A> a;
    private final ArtefactId<B> b;
    private final ArtefactId<C> c;
    private final ArtefactId<D> d;
	private final ArtefactId<T> destination;
	private final With4.Action<A, B, C, D, T> _action;

	public With4(
        ArtefactId<A> a,
        ArtefactId<B> b,
        ArtefactId<C> c,
        ArtefactId<D> d,
        ArtefactId<T> destination,
        With4.Action<A, B, C, D, T> _action
	) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
		this.destination = destination;
		this._action = _action;
	}

	@Override
	public Function<ArtefactMap, T> action() {
		return map -> _action.apply(map.get(a), map.get(b), map.get(c), map.get(d));
	}

    @Override
    public Set<ArtefactId<?>> sources() {
        return Set.of(a, b, c, d);
    }

	@Override
	public ArtefactId<T> destination() {
		return destination;
	}

    @FunctionalInterface
    public interface Action<A,B,C,D,T> {
        T apply(A a, B b, C c, D d);
    }
}
