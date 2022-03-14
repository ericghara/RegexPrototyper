package org.ericghara;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegExResults {

    private int matchLines;
    private final List<LineNumberedMatchResult> matches;
    private final int[] lineDataKey;

    public RegExResults(TextFile file, String regEx) {
        Objects.requireNonNull(file, "Received a null file");
        Objects.requireNonNull(regEx, "Received a null regEx");
        matches = new ArrayList<>();
        lineDataKey = new int[file.getNumLines()+1];
        doMatching(regEx, file);
    }

    void doMatching(String regex, TextFile file) {
        int n = 0;
        var matcher = Pattern.compile(regex)
                                      .matcher("");
        for (var line : file) {
            matcher.reset(line);
            final var finN = n;
            int lineMatches =
                    (int) matcher.results()
                                 .peek((r) ->
                                         matches.add(new LineNumberedMatchResult(r, finN) ) )
                                 .count();
            lineDataKey[n + 1] = lineDataKey[n++] + lineMatches;
            if (lineMatches > 0) {
                matchLines++;
            }
        }
    }

    public int getNumLinesParsed() {
        return lineDataKey.length-1;
    }

    public int getNumMatches() {
        return matches.size();
    }

    public int getNumLinesWithMatch() {
        return matchLines;
    }

    public Stream<LineNumberedMatchResult> stream() {
        return matches.stream();
    }

    public int getNumMatchesOn(int lineNum) {
        return lineDataKey[lineNum+1] - lineDataKey[lineNum];
    }

    public List<LineNumberedMatchResult> getResultsFor(int lineNum) {
        int start = lineDataKey[lineNum];
        int stop = lineDataKey[lineNum+1];
        var list = new ArrayList<LineNumberedMatchResult>(stop-start);
        for (int i = start; i < stop; i++) {
            list.add(matches.get(i) );
        }
        return list;
    }
}
