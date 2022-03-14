package org.ericghara;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.*;

class RegExResultsTest {

    static RegExResults results;
    static List<String> text = List.of(
            "aaaaa",
            "b",
            "",
            "a a a"
    );
    static String query = "a";

    @BeforeAll
    static void before() {
        TextFile file = mock(TextFile.class);
        when(file.iterator() ).thenReturn(text.iterator() );
        when(file.getNumLines() ).thenReturn(text.size() );
        results = new RegExResults(file, "a");
    }

    @Test
    void getNumLinesParsed() {
        Assertions.assertEquals(text.size(), results.getNumLinesParsed() );
    }

    @Test
    void getNumMatches() {
        Assertions.assertEquals(results.getNumMatches(), 8);
    }

    @Test
    void getNumLinesWithMatch() {
        var expected = text.stream()
                                 .filter( (s) -> s.contains(query) )
                                 .count();
        Assertions.assertEquals(expected, results.getNumLinesWithMatch() );
    }

    @Test
    void getNumMatchesOn() {
        Assertions.assertEquals(5, results.getNumMatchesOn(0) );
        Assertions.assertEquals(0, results.getNumMatchesOn(1) );
        Assertions.assertEquals(0, results.getNumMatchesOn(2) );
        Assertions.assertEquals( 3, results.getNumMatchesOn(3));
    }

    @Test
    void getResultsFor() {
        Function<Integer,String[]> stringResults = (i) -> results.getResultsFor(i)
                                                            .stream().map(LineNumberedMatchResult::group)
                                                            .toArray(String[]::new);
        var lineA = 0;
        Assertions.assertArrayEquals(text.get(lineA).split(""), stringResults.apply(lineA) );
        var lineB = 1;
        Assertions.assertArrayEquals(new String[0], stringResults.apply(lineB) );
        var lineC = 3;
        Assertions.assertArrayEquals(text.get(lineC).split(" "), stringResults.apply(lineC) );
    }
}