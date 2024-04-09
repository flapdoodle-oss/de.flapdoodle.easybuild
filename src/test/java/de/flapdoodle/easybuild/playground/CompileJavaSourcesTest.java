package de.flapdoodle.easybuild.playground;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class CompileJavaSourcesTest {

    @Test
    void compileSampleProject(@TempDir Path target) {
        var javaSources = new JavaSources(sampleProjectBasePath());
        var classes = new CompileJavaSources().compile(javaSources, target);
        assertThat(classes)
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorld.class"));
    }

    private static Path sampleProjectBasePath() {
        URL readMeAsResource = CompileJavaSourcesTest.class.getResource("/sample-project/README.md");
        assertThat(readMeAsResource).isNotNull();
        assertThat(readMeAsResource.getProtocol()).isEqualTo("file");
        return Paths.get(readMeAsResource.getPath()).getParent();
    }

}
