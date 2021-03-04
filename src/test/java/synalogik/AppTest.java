package synalogik;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.TreeMap;

class AppTest {

    @Test
    void emptyTextState_CreatesReportWithZeroAverageWordLength() {
        var textState = new ReadTextState();
        var report = textState.toReport();
        assertEquals(0, report.getAverageWordLength());
    }

    @Test
    void textState_WithSpaceSeperatedString_CountsCorrectly() {
        var report = runTextCount(new String[] { "a bb cc ddd eee fff" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(1, 1);
        expectedMap.put(2, 2);
        expectedMap.put(3, 3);
        var expectedWordCount = 6;
        var expectedLetterCount = 14;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 3;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(3);
        
        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
        assertEquals(expectedMap, report.getWordLengthCountLookup());
    }

    @Test
    void textState_WithNoWhiteSpaceSeperatedString_CountsAsASingleWord() {
        var report = runTextCount(new String[] { "ABunchOfWordsNot SeperatedByWhiteSpace" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(16, 1);
        expectedMap.put(21, 1);
        var expectedWordCount = 2;
        var expectedLetterCount = 37;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 1;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(16);
        expectedMostFreqWordLengths.add(21);
        

        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
        assertEquals(expectedMap, report.getWordLengthCountLookup());
    }

    @Test
    void textState_WithMultipleSpaces_AddsNoExtraWords() {
        var report = runTextCount(new String[] { "A              LONG                    GAP" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(1, 1);
        expectedMap.put(3, 1);
        expectedMap.put(4, 1);
        var expectedWordCount = 3;
        var expectedLetterCount = 8;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 1;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(1);
        expectedMostFreqWordLengths.add(3);
        expectedMostFreqWordLengths.add(4);

        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
        assertEquals(expectedMap, report.getWordLengthCountLookup());
    }

    @Test
    void textState_WithMultipleStringsWithGapHandled_CountsAsASingleWord() {
        var report = runTextCount(new String[] { "A Lovely ", "Sentence" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(1, 1);
        expectedMap.put(6, 1);
        expectedMap.put(8, 1);
        var expectedWordCount = 3;
        var expectedLetterCount = 15;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 1;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(1);
        expectedMostFreqWordLengths.add(6);
        expectedMostFreqWordLengths.add(8);
        

        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
        assertEquals(expectedMap, report.getWordLengthCountLookup());
    }

    @Test
    void textState_WithMultipleStringsWithNoGapHandled_CountsAsASingleWord() {
        var report = runTextCount(new String[] { "A Lovely", "Sentence" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(1, 1);
        expectedMap.put(14, 1);
        var expectedWordCount = 2;
        var expectedLetterCount = 15;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 1;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(1);
        expectedMostFreqWordLengths.add(14);
        

        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
        assertEquals(expectedMap, report.getWordLengthCountLookup());
    }

    @Test
    void textState_WithStringWithPunctuation_CountsWordsCorrectly() {
        var report = runTextCount(new String[] { "A, Somewhat( Large) Set? Of. Punctuation: Which; Should\" be\' ignored!" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(1, 1);
        expectedMap.put(2, 2);
        expectedMap.put(3, 1);
        expectedMap.put(5, 2);
        expectedMap.put(6, 1);
        expectedMap.put(7, 1);
        expectedMap.put(8, 1);
        expectedMap.put(11, 1);
        var expectedWordCount = 10;
        var expectedLetterCount = 50;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 2;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(2);
        expectedMostFreqWordLengths.add(5);

        assertEquals(expectedMap, report.getWordLengthCountLookup());
        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
        
    }

    @Test
    void textState_WithStringWithPunctuationWithNoGaps_CountsWordsCorrectly() {
        var report = runTextCount(new String[] { "A,Somewhat(Large)Set?Of.Punctuation:Which;Should\"be\'ignored!" });

        var expectedMap = new TreeMap<Integer, Integer>();
        expectedMap.put(1, 1);
        expectedMap.put(2, 2);
        expectedMap.put(3, 1);
        expectedMap.put(5, 2);
        expectedMap.put(6, 1);
        expectedMap.put(7, 1);
        expectedMap.put(8, 1);
        expectedMap.put(11, 1);
        var expectedWordCount = 10;
        var expectedLetterCount = 50;
        var expectedAvgWordLen = (double) expectedLetterCount / expectedWordCount;
        var mostFrequentWordLengthCount = 2;
        var expectedMostFreqWordLengths = new ArrayList<Integer>();
        expectedMostFreqWordLengths.add(2);
        expectedMostFreqWordLengths.add(5);

        assertEquals(expectedMap, report.getWordLengthCountLookup());
        assertEquals(expectedAvgWordLen, report.getAverageWordLength());
        assertEquals(mostFrequentWordLengthCount, report.getMostFrequentWordLengthCount());
        assertArrayEquals(expectedMostFreqWordLengths.toArray(), report.getMostFrequentWordLengths().toArray());
        assertEquals(expectedWordCount, report.getWordCount());
    }

    /**
     * Runs the standard text counting procedure
     */
    private TextCountingReport runTextCount(String[] textLines) {
        var textState = new ReadTextState();
        for (String line : textLines) {
            textState.handleTextLine(line);
        }
        textState.markCompleted();
        return textState.toReport();
    }
}
