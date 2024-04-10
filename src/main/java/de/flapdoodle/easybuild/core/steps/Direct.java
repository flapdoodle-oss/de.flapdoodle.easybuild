package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Map;
import java.util.function.Function;

public abstract class Direct<A, T> implements BuildStep {
    private final ArtefactSet.Single<A> source;
    private final ArtefactSet.Single<T> destination;
    private final Function<A, T> _action;

    public Direct(
        ArtefactSet.Single<A> source,
        ArtefactSet.Single<T> destination,
        Function<A, T> _action
    ) {
        this.source = source;
        this.destination = destination;
        this._action = _action;
    }

    @Override
    public Function<ArtefactMap, ArtefactMap> action() {
        return map -> {
            var result = _action.apply(map.get(source.a()));
            return ArtefactMap.of(destination.a(), result);
        };
    }

    @Override
    public ArtefactSet.Single<A> source() {
        return source;
    }

    @Override
    public ArtefactSet.Single<T> destination() {
        return destination;
    }
}
