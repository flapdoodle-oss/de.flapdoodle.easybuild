package de.flapdoodle.easybuild.core;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ArtefactMap {
    <T> Optional<T> find(ArtefactId<T> artefact);

    default <T> T get(ArtefactId<T> artefact) {
        return Objects.requireNonNull(find(artefact).orElse(null),"could not find "+artefact+" in "+this);
    }

    default ArtefactMap or(ArtefactMap fallback) {
        var that = this;

        return new ArtefactMap() {
            @Override
            public <T> Optional<T> find(final ArtefactId<T> artefact) {
                Optional<T> result = that.find(artefact);
                return result.isPresent()
                    ? result
                    : fallback.find(artefact);
            }

            @Override
            public String toString() {
                return that+"(fallback: "+fallback+")";
            }
        };
    }

    static ArtefactMap with(Entry<?> ... entries) {
        var map = Stream.of(entries)
            .collect(Collectors.toMap(Entry::key, Entry::value));
        return new ArtefactMap() {
            @Override
            public <T> Optional<T> find(final ArtefactId<T> artefact) {
                return Optional.ofNullable((T) map.get(artefact));
            }

            @Override
            public String toString() {
                return "ArtefactMap(\n"+map.entrySet().stream().map(Object::toString).collect(Collectors.joining("\n"))+")";
            }
        };
    }

    static <A> ArtefactMap of(ArtefactId<A> key, A value) {
        return with(entry(key, value));
    }
    
    static <A, B> ArtefactMap of(ArtefactId<A> key, A value, ArtefactId<B> keyB, B valueB) {
        return with(entry(key, value), entry(keyB, valueB));
    }

    static <A, B, C> ArtefactMap of(ArtefactId<A> key, A value, ArtefactId<B> keyB, B valueB, ArtefactId<C> keyC, C valueC) {
        return with(entry(key, value), entry(keyB, valueB), entry(keyC, valueC));
    }

    record Entry<T>(ArtefactId<T> key, T value) {}

    static <T> Entry<T> entry(ArtefactId<T> key, T value) {
        return new Entry<>(key, value);
    }
}
