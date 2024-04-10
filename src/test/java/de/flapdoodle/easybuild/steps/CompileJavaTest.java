package de.flapdoodle.easybuild.steps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CompileJavaTest {

    @Test
    void compileSampleProject(@TempDir Path target) {
        var testee = new CompileJava();

        assertThat(testee.source().values())
            .containsExactlyInAnyOrder(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(JavaSource.class)
            );

        assertThat(testee.destination().values())
            .containsExactly(ArtefactId.ofType(JavaClasses.class));

        var sampleProjectBase = sampleProjectBasePath();

        var sourcesMap = ArtefactMap.with(Map.of(
            ArtefactId.ofType(ProjectBasePath.class), new ProjectBasePath(target),
            ArtefactId.ofType(JavaSource.class), new JavaSource(sampleProjectBase.resolve("src/main/java"))
        ));

        ArtefactMap result = testee.action().apply(sourcesMap);

        var classes = result.get(ArtefactId.ofType(JavaClasses.class));
        
        assertThat(classes.path())
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorld.class"));
    }

    static Path sampleProjectBasePath() {
        URL readMeAsResource = CompileJavaTest.class.getResource("/sample-project/README.md");
        assertThat(readMeAsResource).isNotNull();
        assertThat(readMeAsResource.getProtocol()).isEqualTo("file");
        return Paths.get(readMeAsResource.getPath()).getParent();
    }
}
