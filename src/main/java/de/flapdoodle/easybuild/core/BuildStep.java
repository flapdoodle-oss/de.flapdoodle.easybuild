package de.flapdoodle.easybuild.core;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface BuildStep {
    ArtefactSet source();

    ArtefactSet destination();

    Function<ArtefactMap, ArtefactMap> action();

}
