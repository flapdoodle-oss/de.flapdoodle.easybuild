package de.flapdoodle.easybuild.steps;

public interface ArtefactId<T> {
    record ClassId<T>(Class<T> type) implements ArtefactId<T> {

    }

    static <T> ArtefactId<T> ofType(Class<T> type) {
        return new ClassId(type);
    }
}
