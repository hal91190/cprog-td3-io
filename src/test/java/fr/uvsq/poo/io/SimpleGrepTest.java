package fr.uvsq.poo.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleGrepTest {
    public static final String FILE_CONTENT = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
            Sed non risus. Suspendisse TROUVÉ lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor.
            Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi.
            Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat.
            Duis semper. Duis arcu massa, scelerisque TROUVÉ vitae, consequat in, pretium a, enim. Pellentesque congue.
            Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue.
            Praesent egestas leo in pede.
            Praesent blandit odio eu enim.
            Pellentesque sed dui ut augue blandit sodales.
            Vestibulum ante ipsum primis in faucibus orci luctus TROUVÉ et ultrices posuere cubilia Curae; Aliquam nibh.
            Mauris ac mauris sed pede pellentesque fermentum.
            Maecenas adipiscing ante non diam sodales hendrerit.
            """;
    public static final String SEARCH_STRING = "TROUVÉ";

    @TempDir
    Path tempDir;

    Path file;

    @BeforeEach
    public void setUp() throws IOException {
        file = tempDir.resolve("testfile.txt");
        Files.writeString(file, FILE_CONTENT);
    }

    @Test
    public void simpleGrep() throws IOException {
        List<Indexed<String>> expected = List.of(
                Indexed.index(1, "Sed non risus. Suspendisse TROUVÉ lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor."),
                Indexed.index(4, "Duis semper. Duis arcu massa, scelerisque TROUVÉ vitae, consequat in, pretium a, enim. Pellentesque congue."),
                Indexed.index(9, "Vestibulum ante ipsum primis in faucibus orci luctus TROUVÉ et ultrices posuere cubilia Curae; Aliquam nibh.")
        );
        List<Indexed<String>> found = StreamUtils
                .zipWithIndex(Files.lines(file))
                .filter(i -> i.getValue().contains(SEARCH_STRING))
                .collect(Collectors.toList());

        assertEquals(expected, found);
    }
}
