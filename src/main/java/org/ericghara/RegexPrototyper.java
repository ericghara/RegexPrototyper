package org.ericghara;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.lang.String.format;

public class RegexPrototyper {

    static Consumer<LineNumberedMatchResult> matchPrinter = RegexPrototyper::defaultPrintFormat;

    private final TextFile file;

    public RegexPrototyper(TextFile file) {
        this.file = file;
    }

    public RegexPrototyper(String pathStr) {
       this(new TextFile(pathStr) );
    }

    private static void defaultPrintFormat(LineNumberedMatchResult match) {
        var index = String.format("%d:%d-%d:", match.lineNumber(), match.start(), match.end() );
        System.out.printf("%-15s%s%n", index, match.group() );
    }

    public void printMatches(RegExResults results) {
        System.out.printf("%-15s%s%n", "Location:",	"Matches:");
        int numMatches = results.getNumMatches();
        long uniqueLines = results.getNumLinesWithMatch();
        results.stream()
               .forEach(matchPrinter);
        System.out.printf("%n Found %d matches. %d of %d lines contained a match.%n",
                numMatches, uniqueLines, file.getNumLines() );
    }

    public void printStartupMessage() {
        var filePath = file.getPath();
        var numLines = file.getNumLines();
        var startMessage = List.of("Welcome to Regex Prototyper!",
                format("You are currently matching %s with %d lines read in", filePath, numLines),
                "Enter regular expressions (including escapes!) just as you would in java code.",
                "The only preprocessing is trailing whitespace removal",
                "Press CTRL + D or CTRL + Z to exit");
        startMessage.forEach(System.out::println);
    }

    void  inputLoop() {
        var in = new Scanner(System.in);
        System.out.print("Enter Regex: ");
        while(in.hasNextLine() ) {
            var query = in .nextLine();
            System.out.printf("%nRegular Expression: %s%n", query);
            var results = new RegExResults(file, query);
            printMatches(results);
            System.out.print("Enter Regex: ");
        }
        in.close();
    }

    static void validateArgs(String[] args) throws IllegalArgumentException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Improper usage enter a file path as a single argument.  Paths with spaces are not supported.");
        }

    }


    public static void main(String[] args) {
        validateArgs(args);
        var regEx = new RegexPrototyper(args[0]);
        regEx.printStartupMessage();
        regEx.inputLoop();
    }

}
