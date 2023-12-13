package fr.uvsq.poo.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static java.nio.file.Files.newBufferedReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineCounterTest {
    public static final String DUMMY_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    public static final int NB_LINES = 57;

    @TempDir
    Path tempDir;

    Path file;

    @BeforeEach
    public void setUp() throws IOException {
        file = tempDir.resolve("testfile.txt");
        Files.write(file, Collections.nCopies(NB_LINES, DUMMY_STRING));
    }

    @Test
    public void withLineNumberReader() throws IOException {
        LineNumberReader lnr = new LineNumberReader(newBufferedReader(file));
        while (lnr.readLine() != null) ;
        assertEquals(NB_LINES, lnr.getLineNumber());
    }

    @Test
    public void withFileLines() throws IOException {
        assertEquals(NB_LINES, Files.lines(file).count());
    }
}
