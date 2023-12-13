package fr.uvsq.poo.io;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParsedCommandLineTest {
    @ParameterizedTest
    @ValueSource(strings = { "", "one_arg_only", "too many args",
            "-e one_arg_only", "-e too many args",
            "-f one_arg_only", "-f too many args"
    })
    public void rejectInvalidCommandLines(String args) throws ParseException {
        ParsedCommandLine parsedCommandLine = new ParsedCommandLine(args.split(" "));
        assertFalse(parsedCommandLine.isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = { "-e -f", "-e", "-f"})
    public void throwParseExceptionWhenMissingArgument(String args) {
        assertThrows(ParseException.class, () -> new ParsedCommandLine(args.split(" ")));
    }
}
