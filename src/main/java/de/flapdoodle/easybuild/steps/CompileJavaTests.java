package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.steps.Merge3;
import de.flapdoodle.easybuild.core.steps.Merge4;

import java.nio.file.Path;

public final class CompileJavaTests extends Merge4<ProjectBasePath, ClassPath, JavaTestSource, JavaClasses, JavaTestClasses> {
    public CompileJavaTests(
        ArtefactSet.Quadruple<ProjectBasePath, ClassPath, JavaTestSource, JavaClasses> source,
        ArtefactSet.Single<JavaTestClasses> destination
    ) {
        super(source, destination, (projectBasePath, classPath, javaTestSource, javaClasses) -> {
            Path target = projectBasePath.path().resolve("target").resolve("testClasses");
            new Compiler(projectBasePath.path(), javaTestSource.path(), target)
                .addClasses(javaClasses.path())
                .addJars(classPath.paths())
                .compile();
            return new JavaTestClasses(target);
        });
    }

    public CompileJavaTests() {
        this(
            new ArtefactSet.Quadruple<>(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(ClassPath.class),
                ArtefactId.ofType(JavaTestSource.class),
                ArtefactId.ofType(JavaClasses.class)
            ),
            new ArtefactSet.Single<>(
                ArtefactId.ofType(JavaTestClasses.class)
            )
        );
    }
}
