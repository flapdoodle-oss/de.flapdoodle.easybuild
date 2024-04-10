package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.SampleProject;
import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactMap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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

        var sampleProjectBase = SampleProject.basePath();

        var sourcesMap = ArtefactMap.of(
            ArtefactId.ofType(ProjectBasePath.class), new ProjectBasePath(target),
            ArtefactId.ofType(JavaSource.class), new JavaSource(sampleProjectBase.resolve("src/main/java")
        ));

        ArtefactMap result = testee.action().apply(sourcesMap);

        var classes = result.get(ArtefactId.ofType(JavaClasses.class));
        
        assertThat(classes.path())
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorld.class"));
    }

    @Test
    void compileSampleProjectAndTests(@TempDir Path target) {
        var compileJava = new CompileJava();
        var testee = new CompileJavaTests();

        assertThat(testee.source().values())
            .containsExactlyInAnyOrder(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(ClassPath.class),
                ArtefactId.ofType(JavaClasses.class),
                ArtefactId.ofType(JavaTestSource.class)
            );

        assertThat(testee.destination().values())
            .containsExactly(ArtefactId.ofType(JavaTestClasses.class));

        var sampleProjectBase = SampleProject.basePath();

        var sourcesMap = ArtefactMap.of(
            ArtefactId.ofType(ProjectBasePath.class), new ProjectBasePath(target),
            ArtefactId.ofType(JavaSource.class), new JavaSource(sampleProjectBase.resolve("src/main/java")
        ));

        ArtefactMap result = compileJava.action().apply(sourcesMap);

        var classes = result.get(ArtefactId.ofType(JavaClasses.class));

        assertThat(classes.path())
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorld.class"));

        // TODO hack
        String classPathFromRuntime = System.getProperty("java.class.path");
        var cp = new ClassPath(Arrays.stream(classPathFromRuntime.split(":")).map(it -> Paths.get(it)).toList());

        var testSourcesMap = sourcesMap
            .or(result)
            .or(ArtefactMap.of(
                ArtefactId.ofType(JavaTestSource.class), new JavaTestSource(sampleProjectBase.resolve("src/test/java"))
            ))
            .or(ArtefactMap.of(
                ArtefactId.ofType(ClassPath.class), cp)
            );

        ArtefactMap testResult = testee.action().apply(testSourcesMap);

        var testClasses = testResult.get(ArtefactId.ofType(JavaTestClasses.class));

        assertThat(testClasses.path())
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorldTest.class"));

    }
}
