package de.flapdoodle.easybuild.playground;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompileJavaSources {

    public Path compile(JavaSources sources, Path target) {
        try {
            Path classesPath = Files.createDirectory(target.resolve("classes"));
            List<String> sourceFiles;
            try (var it = Files.walk(sources.sources())) {
                sourceFiles = it
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString())
                    .collect(Collectors.toList());
            }
            var javaSourceList = Files.write(target.resolve("allSources.txt"), sourceFiles, StandardOpenOption.CREATE);

            var process = new ProcessBuilder("javac", "-d", classesPath.toAbsolutePath().toString(), "@"+ javaSourceList)
                .directory(sources.basePath().toFile())
                .inheritIO()
                .start();

            process.waitFor(10, TimeUnit.MINUTES);
            return classesPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
				catch (InterruptedException e) {
					throw new RuntimeException("execution interrupted: ", e);
				}
		}
}
