package fr.uvsq.poo.io;

import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public enum CharStreamDemo {
    APP;

    public void run(String[] args) throws ParseException, IOException {
        ParsedCommandLine parsedCommandLine = new ParsedCommandLine(args);
        if (!parsedCommandLine.isValid()) {
            System.err.println("Invalid arguments");
            parsedCommandLine.printHelp();
        } else if (parsedCommandLine.hasHelp()) {
            parsedCommandLine.printHelp();
        } else {
            Pattern pattern = Pattern.compile(parsedCommandLine.getPattern(), parsedCommandLine.isCaseSensitive());
            GrepReader grepReader = new GrepReader(newBufferedReader(parsedCommandLine.getPath()), pattern);
            while ((grepReader.readLine()) != null) ; // consume the stream
            if (parsedCommandLine.hasLineNumbers()) {
                grepReader.getMatchesWithIndexes().stream()
                        .map(i -> String.format("%5s\t%s", i.getIndex(), i.getValue()))
                        .forEach(System.out::println);
            } else {
                grepReader.getMatchesWithIndexes().stream()
                        .map(i -> i.getValue())
                        .forEach(System.out::println);
            }
        }
    }

    public static void main(String[] args) throws ParseException, IOException {
        APP.run(args);
    }
}
