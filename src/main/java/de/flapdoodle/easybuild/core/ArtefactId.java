package de.flapdoodle.easybuild.core;

public interface ArtefactId<T> {
    record ClassId<T>(Class<T> type) implements ArtefactId<T> {}
    record Named<T>(String name, Class<T> id) implements ArtefactId<T> {}

    static <T> ArtefactId<T> ofType(Class<T> type) {
        return new ClassId<>(type);
    }

    static <T> ArtefactId<T> namedType(String name, Class<T> type) {
        return new Named<>(name, type);
    }
}
