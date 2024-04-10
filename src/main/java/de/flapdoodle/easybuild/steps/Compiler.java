package de.flapdoodle.easybuild.steps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public record Compiler(
    Path basePath,
    Path sources,
    Path target
) {

    boolean compile() {
        try {
            Path classesPath = Files.createDirectories(target);
            List<String> sourceFiles;
            try (var it = Files.walk(sources)) {
                sourceFiles = it
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString())
                    .collect(Collectors.toList());
            }
            var javaSourceList = Files.write(target.resolve("allSources.txt"), sourceFiles, StandardOpenOption.CREATE);

            var process = new ProcessBuilder("javac", "-d", classesPath.toAbsolutePath().toString(), "@"+ javaSourceList)
                .directory(basePath.toFile())
                .inheritIO()
                .start();

            process.waitFor(10, TimeUnit.MINUTES);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException("execution interrupted: ", e);
        }
    }
}
