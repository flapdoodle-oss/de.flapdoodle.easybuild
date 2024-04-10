package de.flapdoodle.easybuild.core;

import java.util.function.Function;

public interface BuildStep<T> {
    ArtefactSet source();

    ArtefactId<T> destination();

    Function<ArtefactMap, T> action();

}
