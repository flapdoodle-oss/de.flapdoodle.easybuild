package de.flapdoodle.easybuild.playground;

import de.flapdoodle.easybuild.SampleProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CompileJavaSourcesTest {

    @Test
    void compileSampleProject(@TempDir Path target) {
        var javaSources = new JavaSources(SampleProject.basePath());
        var classes = new CompileJavaSources().compile(javaSources, target);
        assertThat(classes)
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorld.class"));
    }
}
