package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Set;
import java.util.function.Function;

public abstract class With1<A, T> implements BuildStep<T> {
    private final ArtefactId<A> a;
    private final ArtefactId<T> destination;
    private final Function<A, T> _action;

    public With1(
        ArtefactId<A> a,
        ArtefactId<T> destination,
        Function<A, T> _action
    ) {
        this.a = a;
        this.destination = destination;
        this._action = _action;
    }

    @Override
    public Function<ArtefactMap, T> action() {
        return map -> _action.apply(map.get(a));
    }

    @Override
    public Set<ArtefactId<?>> sources() {
        return Set.of(a);
    }

    @Override
    public ArtefactId<T> destination() {
        return destination;
    }
}
