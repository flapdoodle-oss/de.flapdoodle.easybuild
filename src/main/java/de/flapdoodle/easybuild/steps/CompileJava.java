package de.flapdoodle.easybuild.steps;

import java.nio.file.Path;

public final class CompileJava extends BuildStep.Merge<ProjectBasePath, JavaSource, JavaClasses> {
    public CompileJava() {
        this(
            new ArtefactSet.Double<>(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(JavaSource.class)
            ),
            new ArtefactSet.Single<>(
                ArtefactId.ofType(JavaClasses.class)
            )
        );
    }

    public CompileJava(
        ArtefactSet.Double<ProjectBasePath, JavaSource> source,
        ArtefactSet.Single<JavaClasses> destination
    ) {
        super(source, destination, (projectBasePath, javaSource) -> {
            Path target = projectBasePath.path().resolve("target").resolve("classes");
            new Compiler(projectBasePath.path(), javaSource.path(), target).compile();
            return new JavaClasses(target);
        });
    }
}
