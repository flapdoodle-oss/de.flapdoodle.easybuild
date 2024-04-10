package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.steps.With4;

import java.nio.file.Path;

public final class CompileJavaTests extends With4<ProjectBasePath, ClassPath, JavaTestSource, JavaClasses, JavaTestClasses> {
    public CompileJavaTests(
        ArtefactId<ProjectBasePath> a, ArtefactId<ClassPath> b, ArtefactId<JavaTestSource> c, ArtefactId<JavaClasses> d,
        ArtefactId<JavaTestClasses> destination
    ) {
        super(a, b, c, d, destination, (projectBasePath, classPath, javaTestSource, javaClasses) -> {
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
            ArtefactId.ofType(ProjectBasePath.class),
            ArtefactId.ofType(ClassPath.class),
            ArtefactId.ofType(JavaTestSource.class),
            ArtefactId.ofType(JavaClasses.class),
            ArtefactId.ofType(JavaTestClasses.class)
        );
    }
}
