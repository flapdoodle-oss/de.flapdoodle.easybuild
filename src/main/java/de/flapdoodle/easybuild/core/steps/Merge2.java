package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Merge2<A, B, T> implements BuildStep {
    private final ArtefactSet.Tuple<A, B> source;
    private final ArtefactSet.Single<T> destination;
    private final BiFunction<A, B, T> _action;

    public Merge2(
        ArtefactSet.Tuple<A, B> source,
        ArtefactSet.Single<T> destination,
        BiFunction<A, B, T> _action
    ) {
        this.source = source;
        this.destination = destination;
        this._action = _action;
    }

    @Override
    public Function<ArtefactMap, ArtefactMap> action() {
        return map -> {
            var result = _action.apply(map.get(source.a()), map.get(source.b()));
            return ArtefactMap.with(Map.of(destination.a(), result));
        };
    }

    @Override
    public ArtefactSet.Tuple<A, B> source() {
        return source;
    }

    @Override
    public ArtefactSet.Single<T> destination() {
        return destination;
    }
}
