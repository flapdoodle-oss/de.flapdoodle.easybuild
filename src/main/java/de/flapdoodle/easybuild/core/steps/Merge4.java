package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.function.Function;

public abstract class Merge4<A, B, C, D, T> implements BuildStep {
	private final ArtefactSet.Quadruple<A, B, C, D> source;
	private final ArtefactSet.Single<T> destination;
	private final Merge4.Action<A, B, C, D, T> _action;

	public Merge4(
		ArtefactSet.Quadruple<A, B, C, D> source,
		ArtefactSet.Single<T> destination,
        Merge4.Action<A, B, C, D, T> _action
	) {
		this.source = source;
		this.destination = destination;
		this._action = _action;
	}

	@Override
	public Function<ArtefactMap, ArtefactMap> action() {
		return map -> {
			var result = _action.apply(map.get(source.a()), map.get(source.b()), map.get(source.c()), map.get(source.d()));
			return ArtefactMap.of(destination.a(), result);
		};
	}

	@Override
	public ArtefactSet.Quadruple<A, B, C, D> source() {
		return source;
	}

	@Override
	public ArtefactSet.Single<T> destination() {
		return destination;
	}

    @FunctionalInterface
    public interface Action<A,B,C,D,T> {
        T apply(A a, B b, C c, D d);
    }
}
