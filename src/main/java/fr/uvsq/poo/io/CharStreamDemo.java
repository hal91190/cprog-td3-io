package fr.uvsq.poo.io;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public enum CharStreamDemo {
    APP;

    private Options cliOptions;

    CommandLine cmdLine;

    private void parseCLI(String[] args) throws ParseException {
        cliOptions = new Options();
        cliOptions.addOption(Option.builder("h").longOpt("help").desc("Display Help").build());
        cliOptions.addOption(Option.builder("e").longOpt("regexp").hasArg().desc("specify a regexp pattern").build());
        cliOptions.addOption(Option.builder("f").longOpt("file").hasArg().desc("read patterns from a file").build());
        cliOptions.addOption(Option.builder("i").longOpt("ignore-case").desc("Ignore case").build());
        cliOptions.addOption(Option.builder("n").longOpt("line-number").desc("Output line numbers").build());

        CommandLineParser parser = new DefaultParser();
        cmdLine = parser.parse(cliOptions, args);
    }
    public void run(String[] args) throws ParseException, IOException {
        parseCLI(args);
        if (cmdLine.hasOption("h") || !isValidCmdLine()) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("mygrep", cliOptions, true);
        } else {
            String stringPattern;
            if (cmdLine.hasOption("e")) {
                stringPattern = cmdLine.getOptionValue("e");
            } else if (cmdLine.hasOption("f")) {
                Path patternsFile = Path.of(cmdLine.getOptionValue("f"));
                stringPattern = Files.lines(patternsFile).collect(Collectors.joining(")|(", "(", ")"));
            } else {
                stringPattern = cmdLine.getArgs()[0];
            }
            int patternFlags = cmdLine.hasOption("i") ? CASE_INSENSITIVE : 0;
            Pattern pattern = Pattern.compile(stringPattern);
            Path fileToProcess = Path.of(cmdLine.getArgs()[cmdLine.hasOption("e") || cmdLine.hasOption("f") ? 0 : 1]);
            GrepReader grepReader = new GrepReader(newBufferedReader(fileToProcess), pattern);
            grepReader.lines().forEach(System.out::println);
        }
    }

    private boolean isValidCmdLine() {
        List<String> parsedAndLeftOverArgs = cmdLine.getArgList();
        // only -e or -f are allowed, not both
        if (cmdLine.hasOption("e") && cmdLine.hasOption("f")) return false;
        // a command line with -e must have an argument for -e and a filename
        if (cmdLine.hasOption("e") &&
                (cmdLine.getOptionValue("e") == null || parsedAndLeftOverArgs.size() != 1)) return false;
        // a command line with -f must have an argument for -f and a filename
        if (cmdLine.hasOption("f") &&
                (cmdLine.getOptionValue("f") == null || parsedAndLeftOverArgs.size() != 1)) return false;
        // a command line without -e or -f must have a pattern and a filename
        if (!cmdLine.hasOption("e") && !cmdLine.hasOption("f") && parsedAndLeftOverArgs.size() != 2) return false;

        return true;
    }

    public static void main(String[] args) throws ParseException, IOException {
        APP.run(args);
    }
}
