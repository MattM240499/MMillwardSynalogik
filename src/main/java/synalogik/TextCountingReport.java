package synalogik;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A set of data containing statistics on reading a piece of text.
 */
public class TextCountingReport {
    private int wordCount;
    private double averageWordLength;
    private Map<Integer, Integer> wordLengthCountLookup;
    private int mostFrequentWordLengthCount;
    private List<Integer> mostFrequentWordLengths;

    public int getWordCount() {
        return wordCount;
    }

    public double getAverageWordLength() {
        return averageWordLength;
    }

    public Map<Integer, Integer> getWordLengthCountLookup() {
        return wordLengthCountLookup;
    }

    public int getMostFrequentWordLengthCount() {
        return mostFrequentWordLengthCount;
    }

    public List<Integer> getMostFrequentWordLengths() {
        return mostFrequentWordLengths;
    }

    public TextCountingReport(int count, double avgLength, Map<Integer, Integer> wordLenLookup, int mostFreqLengthCount,
            List<Integer> mostFreqWordLengths) {
        wordCount = count;
        averageWordLength = avgLength;
        wordLengthCountLookup = wordLenLookup;
        mostFrequentWordLengthCount = mostFreqLengthCount;
        mostFrequentWordLengths = mostFreqWordLengths;
    }

    /**
     * Produces the report as a string.
     */
    @Override
    public String toString() {
        var outputSb = new StringBuilder();
        outputSb.append("Word count = " + wordCount + "\n");
        outputSb.append("Average word length = " + Math.round(averageWordLength * 1000.0) / 1000.0 + "\n");
        for (Map.Entry<Integer, Integer> entry : wordLengthCountLookup.entrySet()) {
            outputSb.append("Number of words of length " + entry.getKey().toString() + " is " + entry.getValue().toString() + "\n");
        }
        outputSb.append("The most frequently occurring word length is " + mostFrequentWordLengthCount
                + ", for word lengths of "
                + mostFrequentWordLengths.stream().map(Object::toString).collect(Collectors.joining(" & ")));
        return outputSb.toString();
    }
}