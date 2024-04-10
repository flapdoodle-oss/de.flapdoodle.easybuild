package de.flapdoodle.easybuild.core.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import de.flapdoodle.easybuild.core.BuildStep;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class With2<A, B, T> implements BuildStep<T> {
    private final ArtefactId<A> a;
    private final ArtefactId<B> b;
    private final ArtefactId<T> destination;
    private final BiFunction<A, B, T> _action;

    public With2(
        ArtefactId<A> a, ArtefactId<B> b,
        ArtefactId<T> destination,
        BiFunction<A, B, T> _action
    ) {
        this.a = a;
        this.b = b;
        this.destination = destination;
        this._action = _action;
    }

    @Override
    public Function<ArtefactMap, T> action() {
        return map -> _action.apply(map.get(a), map.get(b));
    }
    @Override
    public Set<ArtefactId<?>> sources() {
        return Set.of(a, b);
    }

    @Override
    public ArtefactId<T> destination() {
        return destination;
    }
}
