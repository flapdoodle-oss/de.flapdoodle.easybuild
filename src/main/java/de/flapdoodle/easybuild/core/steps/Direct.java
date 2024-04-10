package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.*;

import java.util.Map;
import java.util.function.Function;

public abstract class Direct<A, T> implements BuildStep<T> {
    private final ArtefactSet.Single<A> source;
    private final ArtefactId<T> destination;
    private final Function<A, T> _action;

    public Direct(
        ArtefactSet.Single<A> source,
        ArtefactId<T> destination,
        Function<A, T> _action
    ) {
        this.source = source;
        this.destination = destination;
        this._action = _action;
    }

    @Override
    public Function<ArtefactMap, T> action() {
        return map -> _action.apply(map.get(source.a()));
    }

    @Override
    public ArtefactSet.Single<A> source() {
        return source;
    }

    @Override
    public ArtefactId<T> destination() {
        return destination;
    }
}
