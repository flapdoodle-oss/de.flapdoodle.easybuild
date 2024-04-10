package de.flapdoodle.easybuild.core;

import java.util.Set;
import java.util.function.Function;

public interface BuildStep<T> {
    Set<ArtefactId<?>> sources();

    ArtefactId<T> destination();

    Function<ArtefactMap, T> action();

}
