package de.flapdoodle.easybuild.core;

import java.util.Set;

public sealed interface ArtefactSet {
    Set<ArtefactId<?>> values();

    record Single<A>(ArtefactId<A> a) implements ArtefactSet {

        @Override
        public Set<ArtefactId<?>> values() {
            return Set.of(a);
        }
    }

    record Tuple<A, B>(ArtefactId<A> a, ArtefactId<B> b) implements ArtefactSet {

        @Override
        public Set<ArtefactId<?>> values() {
            return Set.of(a, b);
        }
    }

    record Triple<A, B, C>(ArtefactId<A> a, ArtefactId<B> b, ArtefactId<C> c) implements ArtefactSet {

        @Override
        public Set<ArtefactId<?>> values() {
            return Set.of(a, b, c);
        }
    }
}
