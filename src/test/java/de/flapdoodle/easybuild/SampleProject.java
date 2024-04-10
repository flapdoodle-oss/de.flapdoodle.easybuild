package de.flapdoodle.easybuild;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SampleProject {
    private SampleProject() {
        // no instance
    }


    public static Path basePath() {
        URL readMeAsResource = SampleProject.class.getResource("/sample-project/README.md");
        assertThat(readMeAsResource).isNotNull();
        assertThat(readMeAsResource.getProtocol()).isEqualTo("file");
        return Paths.get(readMeAsResource.getPath()).getParent();
    }
}
