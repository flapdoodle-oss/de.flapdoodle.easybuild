package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.api.Javac;
import de.flapdoodle.easybuild.core.steps.With4;
import de.flapdoodle.easybuild.steps.artefacts.*;

import java.nio.file.Path;

public final class CompileJavaTests extends With4<ProjectBasePath, ClassPath, JavaTestSource, JavaClasses, JavaTestClasses> {
    public CompileJavaTests(
        ArtefactId<ProjectBasePath> a, ArtefactId<ClassPath> b, ArtefactId<JavaTestSource> c, ArtefactId<JavaClasses> d,
        ArtefactId<JavaTestClasses> destination
    ) {
        super(a, b, c, d, destination, (projectBasePath, classPath, javaTestSource, javaClasses) -> {
            Path target = projectBasePath.path().resolve("target").resolve("testClasses");
            Javac.builder()
                .basePath(projectBasePath.path())
                .sources(javaTestSource.path())
                .target(target)
                .addClasses(javaClasses.path())
                .addJars(classPath.paths())
                .build()
                .compile();
            return new JavaTestClasses(target);
        });
    }

    public CompileJavaTests() {
        this(
            ArtefactId.ofType(ProjectBasePath.class),
            ArtefactId.ofType(ClassPath.class),
            ArtefactId.ofType(JavaTestSource.class),
            ArtefactId.ofType(JavaClasses.class),
            ArtefactId.ofType(JavaTestClasses.class)
        );
    }
}
