package fr.uvsq.poo.io;

//TODO remove dependency with protonpack by reimplementing Indexed

import com.codepoetics.protonpack.Indexed;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class takes a reader and a pattern and removes lines that don't match the pattern.
 * Only the methods readLines/lines (not read) are allowed to filter the stream.
 */
public class GrepReader extends BufferedReader {
    /**
     * The pattern to search for.
     */
    private final Pattern pattern;

    private final List<Indexed<String>> matches;

    private long currentLineNumber;

    /**
     * Build the stream from another stream and a regex pattern.
     *
     * @param in      Reader stream
     * @param pattern a regex pattern
     */
    protected GrepReader(Reader in, Pattern pattern) {
        super(in);
        this.pattern = pattern;
        matches = new ArrayList<>();
    }

    /**
     * Return the next line matching the pattern.
     *
     * @return the next line matching the pattern
     * @throws IOException If an I/O error occurs
     */
    @Override
    public String readLine() throws IOException {
        String currentLine;
        while ((currentLine = super.readLine()) != null) {
            currentLineNumber++;
            if (pattern.matcher(currentLine).find()) {
                matches.add(Indexed.index(currentLineNumber, currentLine));
                return currentLine;
            }
        }
        return null;
    }

    /**
     * Get all lines matching the pattern with line numbers.
     *
     * @return a list of couple (line number, line) matching the pattern
     */
    public List<Indexed<String>> getMatchesWithIndexes() {
        return Collections.unmodifiableList(matches);
    }
}
