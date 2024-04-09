package de.flapdoodle.easybuild.playground;

import java.nio.file.Path;

record JavaSources(
    Path basePath
) {
    public Path sources() {
        return basePath.resolve("src").resolve("main");
    }

    public Path testSources() {
        return basePath.resolve("src").resolve("test");
    }
}
