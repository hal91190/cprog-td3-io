package fr.uvsq.poo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
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

    /**
     * Build the stream from another stream and a regex pattern.
     *
     * @param in Reader stream
     * @param pattern a regex pattern
     */
    protected GrepReader(Reader in, Pattern pattern) {
        super(in);
        this.pattern = pattern;
    }

    /**
     * Return the next line matching the pattern.
     *
     * @return the next line matching the pattern
     * @throws IOException
     */
    @Override
    public String readLine() throws IOException {
        String currentLine;
        while ((currentLine = super.readLine()) != null) {
            if (pattern.matcher(currentLine).find()) return currentLine;
        }
        return null;
    }
}
