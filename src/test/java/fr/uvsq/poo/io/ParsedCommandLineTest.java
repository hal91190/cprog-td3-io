package fr.uvsq.poo.io;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ParsedCommandLineTest {
    /**
     * Content of the pattern file.
     */
    public static final String PATTERNS = """
            pattern1
            pattern2
            pattern3
            """;

    @TempDir
    Path tempDir;

    Path file;

    @BeforeEach
    public void setUp() throws IOException {
        file = tempDir.resolve("patterns.txt");
        Files.writeString(file, PATTERNS);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "one_arg_only", "too many args",
            "-e one_arg_only", "-e too many args",
            "-f one_arg_only", "-f too many args"
    })
    public void rejectInvalidCommandLines(String args) throws ParseException {
        var parsedCommandLine = new ParsedCommandLine(args.split(" "));
        assertFalse(parsedCommandLine.isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = { "-e -f", "-e", "-f"})
    public void throwParseExceptionWhenMissingArgument(String args) {
        assertThrows(ParseException.class, () -> new ParsedCommandLine(args.split(" ")));
    }

    @Test
    public void patternShouldBeBuiltFromTheContentOfAFile() throws ParseException {
        var expected = PATTERNS.lines().collect(Collectors.joining(")|(", "(", ")"));
        var args = "-f " + file;
        var parsedCommandLine = new ParsedCommandLine(args.split(" "));
        assertEquals(expected, parsedCommandLine.getPattern().get());
    }

    @Test
    public void patternShouldBeEmptyIfThePatternFileIsNotFound() throws ParseException {
        var args = "-f NOT_EXISTING_FILE.txt";
        var parsedCommandLine = new ParsedCommandLine(args.split(" "));
        assertTrue(parsedCommandLine.getPattern().isEmpty());
    }
}
