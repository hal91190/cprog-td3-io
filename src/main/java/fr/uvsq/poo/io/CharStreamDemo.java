package fr.uvsq.poo.io;

import com.codepoetics.protonpack.Indexed;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.regex.Pattern;

import static java.nio.file.Files.newBufferedReader;

/**
 * The main class.
 */
public enum CharStreamDemo {
    /**
     * The application object.
     */
    APP;

    /**
     * The main method as a non static method.
     * @param args cli arguments
     * @throws ParseException if a problem arise during cli parsing
     * @throws IOException if an I/O error arise reading the file to analyze
     */
    public void run(String[] args) throws ParseException, IOException {
        var parsedCommandLine = new ParsedCommandLine(args);
        if (!parsedCommandLine.isValid()) {
            System.err.println("Invalid arguments");
            parsedCommandLine.printHelp();
        } else if (parsedCommandLine.hasHelp()) {
            parsedCommandLine.printHelp();
        } else {
            var parsedPattern = parsedCommandLine.getPattern();
            if (parsedPattern.isEmpty()) {
                System.err.println("No pattern found");
                parsedCommandLine.printHelp();
            } else {
                var pattern = Pattern.compile(parsedPattern.get(), parsedCommandLine.getPatternFlags());
                var grepReader = new GrepReader(newBufferedReader(parsedCommandLine.getPath()), pattern);
                while ((grepReader.readLine()) != null) ; // consume the stream
                if (parsedCommandLine.hasLineNumbers()) {
                    grepReader.getMatchesWithIndexes().stream()
                            .map(i -> String.format("%5s\t%s", i.getIndex(), i.getValue()))
                            .forEach(System.out::println);
                } else {
                    grepReader.getMatchesWithIndexes().stream()
                            .map(Indexed::getValue)
                            .forEach(System.out::println);
                }
            }
        }
    }

    /**
     * The main method.
     * @param args cli arguments
     * @throws ParseException if a problem arise during cli parsing
     * @throws IOException if an I/O error arise reading the file to analyze
     */
    public static void main(String[] args) throws ParseException, IOException {
        APP.run(args);
    }
}
