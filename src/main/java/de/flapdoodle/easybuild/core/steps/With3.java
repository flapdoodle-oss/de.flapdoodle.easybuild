package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Set;
import java.util.function.Function;

public abstract class With3<A, B, C, T> implements BuildStep<T> {
    private final ArtefactId<A> a;
    private final ArtefactId<B> b;
    private final ArtefactId<C> c;
	private final ArtefactId<T> destination;
	private final With3.Action<A, B, C, T> _action;

	public With3(
        ArtefactId<A> a,
        ArtefactId<B> b,
        ArtefactId<C> c,
		ArtefactId<T> destination,
        With3.Action<A, B, C, T> _action
	) {
        this.a = a;
        this.b = b;
        this.c = c;
		this.destination = destination;
		this._action = _action;
	}

	@Override
	public Function<ArtefactMap, T> action() {
		return map -> _action.apply(map.get(a), map.get(b), map.get(c));
	}

    @Override
    public Set<ArtefactId<?>> sources() {
        return Set.of(a, b, c);
    }

    @Override
	public ArtefactId<T> destination() {
		return destination;
	}

    @FunctionalInterface
    public interface Action<A,B,C,T> {
        T apply(A a, B b, C c);
    }
}
