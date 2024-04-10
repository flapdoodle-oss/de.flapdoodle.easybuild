package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.steps.Merge3;

import java.nio.file.Path;

public final class CompileJavaTests extends Merge3<ProjectBasePath, JavaTestSource, JavaClasses, JavaTestClasses> {
    public CompileJavaTests(
        ArtefactSet.Triple<ProjectBasePath, JavaTestSource, JavaClasses> source,
        ArtefactSet.Single<JavaTestClasses> destination
    ) {
        super(source, destination, (projectBasePath, javaTestSource, javaClasses) -> {
            Path target = projectBasePath.path().resolve("target").resolve("testClasses");
            new Compiler(projectBasePath.path(), javaTestSource.path(), target)
                .addClasses(javaClasses.path())
                .compile();
            return new JavaTestClasses(target);
        });
    }

    public CompileJavaTests() {
        this(
            new ArtefactSet.Triple<>(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(JavaTestSource.class),
                ArtefactId.ofType(JavaClasses.class)
            ),
            new ArtefactSet.Single<>(
                ArtefactId.ofType(JavaTestClasses.class)
            )
        );
    }
}
