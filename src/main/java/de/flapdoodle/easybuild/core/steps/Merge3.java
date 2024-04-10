package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Merge3<A, B, C, T> implements BuildStep<T> {
	private final ArtefactSet.Triple<A, B, C> source;
	private final ArtefactId<T> destination;
	private final Merge3.Action<A, B, C, T> _action;

	public Merge3(
		ArtefactSet.Triple<A, B, C> source,
		ArtefactId<T> destination,
        Merge3.Action<A, B, C, T> _action
	) {
		this.source = source;
		this.destination = destination;
		this._action = _action;
	}

	@Override
	public Function<ArtefactMap, T> action() {
		return map -> {
			var result = _action.apply(map.get(source.a()), map.get(source.b()), map.get(source.c()));
			return result;
		};
	}

	@Override
	public ArtefactSet.Triple<A, B, C> source() {
		return source;
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
