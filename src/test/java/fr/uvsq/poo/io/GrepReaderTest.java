package fr.uvsq.poo.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static java.nio.file.Files.newBufferedReader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrepReaderTest {
    public static final String FILE_CONTENT = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
            Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor.
            Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi.
            Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat.
            Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue.
            Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue.
            Praesent egestas leo in pede.
            Praesent blandit odio eu enim.
            Pellentesque sed dui ut augue blandit sodales.
            Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh.
            Mauris ac mauris sed pede pellentesque fermentum.
            Maecenas adipiscing ante non diam sodales hendrerit.
            """;

    @TempDir
    Path tempDir;

    Path file;

    @BeforeEach
    public void setUp() throws IOException {
        file = tempDir.resolve("testfile.txt");
        Files.writeString(file, FILE_CONTENT);
    }

    @Test
    public void shouldFindNothing() throws IOException {
        var searchString = "NOT FOUND";
        var reader = new GrepReader(newBufferedReader(file), Pattern.compile(searchString));
        var found = reader.lines().toList();
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFilterTwoLinesMatchingASimplePattern() throws IOException {
        var expected = List.of(
                "Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue.",
                "Pellentesque sed dui ut augue blandit sodales."
        );
        var searchString = "Pellentesque";
        var reader = new GrepReader(newBufferedReader(file), Pattern.compile(searchString));
        var found = reader.lines().toList();
        assertEquals(expected, found);
    }

    @Test
    public void shouldFilterLinesMatchingThePattern() throws IOException {
        var expected = List.of(
                "Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat.",
                "Praesent egestas leo in pede.",
                "Praesent blandit odio eu enim."
        );
        var searchString = "^Pr[ao]";
        var reader = new GrepReader(newBufferedReader(file), Pattern.compile(searchString));
        var found = reader.lines().toList();
        assertEquals(expected, found);
    }

    @Test
    public void shouldFilterTwoLinesMatchingASimplePatternCaseInsensitive() throws IOException {
        var expected = List.of(
                "Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue.",
                "Pellentesque sed dui ut augue blandit sodales.",
                "Mauris ac mauris sed pede pellentesque fermentum."
        );
        var searchString = "Pellentesque";
        var reader = new GrepReader(newBufferedReader(file), Pattern.compile(searchString, Pattern.CASE_INSENSITIVE));
        var found = reader.lines().toList();
        assertEquals(expected, found);
    }
}
