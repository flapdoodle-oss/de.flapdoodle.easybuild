package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Merge3<A, B, C, T> implements BuildStep {
	private final ArtefactSet.Triple<A, B, C> source;
	private final ArtefactSet.Single<T> destination;
	private final Merge3.Action<A, B, C, T> _action;

	public Merge3(
		ArtefactSet.Triple<A, B, C> source,
		ArtefactSet.Single<T> destination,
        Merge3.Action<A, B, C, T> _action
	) {
		this.source = source;
		this.destination = destination;
		this._action = _action;
	}

	@Override
	public Function<ArtefactMap, ArtefactMap> action() {
		return map -> {
			var result = _action.apply(map.get(source.a()), map.get(source.b()), map.get(source.c()));
			return ArtefactMap.with(Map.of(destination.a(), result));
		};
	}

	@Override
	public ArtefactSet.Triple<A, B, C> source() {
		return source;
	}

	@Override
	public ArtefactSet.Single<T> destination() {
		return destination;
	}

    @FunctionalInterface
    public interface Action<A,B,C,T> {
        T apply(A a, B b, C c);
    }
}
