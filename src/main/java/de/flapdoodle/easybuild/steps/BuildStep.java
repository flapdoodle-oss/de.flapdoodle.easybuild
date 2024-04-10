package de.flapdoodle.easybuild.steps;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface BuildStep {
    ArtefactSet source();

    ArtefactSet destination();

    Function<ArtefactMap, ArtefactMap> action();

    abstract class Direct<A, T> implements BuildStep {
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
                return ArtefactMap.with(Map.of(destination, result));
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

    abstract class Merge<A, B, T> implements BuildStep {
        private final ArtefactSet.Double<A, B> source;
        private final ArtefactSet.Single<T> destination;
        private final BiFunction<A, B, T> _action;

        public Merge(
            ArtefactSet.Double<A, B> source,
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
        public ArtefactSet.Double<A, B> source() {
            return source;
        }

        @Override
        public ArtefactSet.Single<T> destination() {
            return destination;
        }
    }
}
