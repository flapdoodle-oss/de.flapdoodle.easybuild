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
        // das ist alles was ein build step definieren muss
        var testee = new CompileJava();

        // das sind die Daten die der build step braucht
        assertThat(testee.source().values())
            .containsExactlyInAnyOrder(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(JavaSource.class)
            );

        // das ist das was der build step erzeugt
        assertThat(testee.destination())
            .isEqualTo(ArtefactId.ofType(JavaClasses.class));

        // zwei Klassen, eine davon ein Test, Standardverzeichnislayout
        var sampleProjectBase = SampleProject.basePath();

        // sowas würde automatisch von der z.B. graph basierten abhängigkeitsanalyse befüllt
        // darum muss sich der build step nicht kümmern
        var sourcesMap = ArtefactMap.of(
            ArtefactId.ofType(ProjectBasePath.class), new ProjectBasePath(target),
            ArtefactId.ofType(JavaSource.class), new JavaSource(sampleProjectBase.resolve("src/main/java")
        ));

        // hier würde der build step ausgeführt werden, alles was er braucht bekommt er, auf mehr hat er keinen
        // zugriff
        var classes = testee.action().apply(sourcesMap);

        // prüfe, ob er wirklich den javac erfolgreich angeworfen hat
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

        assertThat(testee.destination())
            .isEqualTo(ArtefactId.ofType(JavaTestClasses.class));

        var sampleProjectBase = SampleProject.basePath();

        var sourcesMap = ArtefactMap.of(
            ArtefactId.ofType(ProjectBasePath.class), new ProjectBasePath(target),
            ArtefactId.ofType(JavaSource.class), new JavaSource(sampleProjectBase.resolve("src/main/java")
        ));

        var classes = compileJava.action().apply(sourcesMap);

        assertThat(classes.path())
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorld.class"));

        // TODO hack
        String classPathFromRuntime = System.getProperty("java.class.path");
        var cp = new ClassPath(Arrays.stream(classPathFromRuntime.split(":")).map(it -> Paths.get(it)).toList());

        var testSourcesMap = sourcesMap
            .or(ArtefactMap.of(
                ArtefactId.ofType(JavaClasses.class), classes
            ))
            .or(ArtefactMap.of(
                ArtefactId.ofType(JavaTestSource.class), new JavaTestSource(sampleProjectBase.resolve("src/test/java"))
            ))
            .or(ArtefactMap.of(
                ArtefactId.ofType(ClassPath.class), cp)
            );

        var testClasses = testee.action().apply(testSourcesMap);

        assertThat(testClasses.path())
            .isDirectoryRecursivelyContaining(it -> it.getFileName().toString().equals("HelloWorldTest.class"));

    }
}
