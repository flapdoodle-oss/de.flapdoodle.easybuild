package de.flapdoodle.easybuild.steps;

import java.util.Set;

public sealed interface ArtefactSet {
    Set<ArtefactId<?>> values();

    record Single<A>(ArtefactId<A> a) implements ArtefactSet {

        @Override
        public Set<ArtefactId<?>> values() {
            return Set.of(a);
        }
    }

    record Double<A, B>(ArtefactId<A> a, ArtefactId<B> b) implements ArtefactSet {

        @Override
        public Set<ArtefactId<?>> values() {
            return Set.of(a, b);
        }
    }
}
