package org.ericghara;

import java.util.regex.MatchResult;

public class LineNumberedMatchResult implements MatchResult {

    private final MatchResult matchResult;
    private final int lineNum;

    public LineNumberedMatchResult(MatchResult matchResult, int lineNum) {
        this.matchResult = matchResult;
        this.lineNum = lineNum;
    }

    public int lineNumber() {
        return lineNum;
    }

    @Override
    public int start() {
        return matchResult.start();
    }

    @Override
    public int start(int group) {
        return matchResult.start(group);
    }

    @Override
    public int end() {
        return matchResult.end();
    }

    @Override
    public int end(int group) {
        return matchResult.end(group);
    }

    @Override
    public String group() {
        return matchResult.group();
    }

    @Override
    public String group(int group) {
        return matchResult.group(group);
    }

    @Override
    public int groupCount() {
        return matchResult.groupCount();
    }
}
