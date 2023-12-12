package fr.uvsq.poo.io;

import org.apache.commons.cli.*;

public enum CharStreamDemo {
    APP;

    private Options cliOptions;

    CommandLine cmdLine;

    private void parseCLI(String[] args) throws ParseException {
        cliOptions = new Options();
        cliOptions.addOption(Option.builder("h").longOpt("help").desc("Display Help").build());
        cliOptions.addOption(Option.builder("e").longOpt("regexp").hasArg().desc("specify a regexp pattern").build());
        cliOptions.addOption(Option.builder("f").longOpt("file").hasArg().desc("read regexp from a file").build());
        cliOptions.addOption(Option.builder("i").longOpt("ignore-case").desc("Ignore case").build());
        cliOptions.addOption(Option.builder("n").longOpt("line-number").desc("Output line numbers").build());

        CommandLineParser parser = new DefaultParser();
        cmdLine = parser.parse(cliOptions, args);
    }
    public void run(String[] args) throws ParseException {
        parseCLI(args);
        if (cmdLine.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("mygrep", cliOptions, true);
        } else {

        }
    }

    public static void main(String[] args) throws ParseException {
        APP.run(args);
    }
}
