package de.flapdoodle.easybuild.core;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        };
    }

    static ArtefactMap with(Map<? super ArtefactId<?>, ?> map) {
        return new ArtefactMap() {
            @Override
            public <T> Optional<T> find(final ArtefactId<T> artefact) {
                return Optional.ofNullable((T) map.get(artefact));
            }

            @Override
            public String toString() {
                return "ArtefactMap("+map+")";
            }
        };
    }
}
