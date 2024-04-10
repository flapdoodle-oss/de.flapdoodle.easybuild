package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.function.Function;

public abstract class Merge4<A, B, C, D, T> implements BuildStep<T> {
	private final ArtefactSet.Quadruple<A, B, C, D> source;
	private final ArtefactId<T> destination;
	private final Merge4.Action<A, B, C, D, T> _action;

	public Merge4(
		ArtefactSet.Quadruple<A, B, C, D> source,
        ArtefactId<T> destination,
        Merge4.Action<A, B, C, D, T> _action
	) {
		this.source = source;
		this.destination = destination;
		this._action = _action;
	}

	@Override
	public Function<ArtefactMap, T> action() {
		return map -> _action.apply(map.get(source.a()), map.get(source.b()), map.get(source.c()), map.get(source.d()));
	}

	@Override
	public ArtefactSet.Quadruple<A, B, C, D> source() {
		return source;
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
