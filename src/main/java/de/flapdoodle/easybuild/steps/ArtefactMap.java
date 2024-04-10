package de.flapdoodle.easybuild.steps;

import java.util.Map;
import java.util.Objects;

public interface ArtefactMap {
    <T> T get(ArtefactId<T> artefact);

    static ArtefactMap with(Map<? super ArtefactId<?>, ?> map) {
        return new ArtefactMap() {
            @Override
            public <T> T get(final ArtefactId<T> artefact) {
                return Objects.requireNonNull((T) map.get(artefact),"could not find "+artefact+" in "+map);
            }
        };
    }
}
