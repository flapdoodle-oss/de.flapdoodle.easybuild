package de.flapdoodle.easybuild.steps;

import de.flapdoodle.easybuild.core.ArtefactId;
import de.flapdoodle.easybuild.core.ArtefactSet;
import de.flapdoodle.easybuild.core.steps.Merge2;

import java.nio.file.Path;

public final class CompileJava extends Merge2<ProjectBasePath, JavaSource, JavaClasses> {
    public CompileJava(
        ArtefactSet.Tuple<ProjectBasePath, JavaSource> source,
        ArtefactId<JavaClasses> destination
    ) {
        super(source, destination, (projectBasePath, javaSource) -> {
            Path target = projectBasePath.path().resolve("target").resolve("classes");
            new Compiler(projectBasePath.path(), javaSource.path(), target).compile();
            return new JavaClasses(target);
        });
    }

    public CompileJava() {
        this(
            new ArtefactSet.Tuple<>(
                ArtefactId.ofType(ProjectBasePath.class),
                ArtefactId.ofType(JavaSource.class)
            ),
            ArtefactId.ofType(JavaClasses.class)
        );
    }

}
