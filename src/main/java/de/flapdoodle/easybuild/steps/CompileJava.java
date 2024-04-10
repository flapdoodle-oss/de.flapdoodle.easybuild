package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.steps.With2;

import java.nio.file.Path;

public final class CompileJava extends With2<ProjectBasePath, JavaSource, JavaClasses> {
    public CompileJava(
        ArtefactId<ProjectBasePath> a, ArtefactId<JavaSource> b,
        ArtefactId<JavaClasses> destination
    ) {
        super(a, b, destination, (projectBasePath, javaSource) -> {
            Path target = projectBasePath.path().resolve("target").resolve("classes");
            new Compiler(projectBasePath.path(), javaSource.path(), target).compile();
            return new JavaClasses(target);
        });
    }

    public CompileJava() {
        this(
            ArtefactId.ofType(ProjectBasePath.class),
            ArtefactId.ofType(JavaSource.class),
            ArtefactId.ofType(JavaClasses.class)
        );
    }

}
