package synalogik;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Holds statistics regarding a given piece of text that has been read consecutively.
 */
public class ReadTextState {
    private static char[] punctuation = { '.', ',', ';', ':', '?', '!', '"', ')', '(' };

    private int lettersRead = 0;
    private int wordsRead = 0;
    private Map<Integer, Integer> wordLengthCountLookup = new TreeMap<>();
    private int currentWordLength = 0;
    private boolean completed = false;

    /**
     * Updates the given statistics by processing the next string
     */
    public void handleTextLine(String textLine) throws IllegalStateException {
        if (completed) {
            throw new IllegalStateException("Text state cannot be updated after is has been marked as completed");
        }
        for (int i = 0; i < textLine.length(); i++) {
            var currentChar = textLine.charAt(i);
            // Treat all whitespace as a space
            if (Character.isWhitespace(currentChar) || isPunctuation(currentChar)) {
                markWordCompleted();
            }

            // Not whitespace, so assume it's a reasonable letter.
            else {
                currentWordLength++;
            }
        }
    }

    /**
     * Marks the text state as having been fully read.
     */
    public void markCompleted() {
        markWordCompleted();
        completed = true;
    }

    /**
     * Converts the existing data to a report.
     * @return a TextCountingReport object
     */
    public TextCountingReport toReport() {
        var mostCommonWordLengthCount = 0;
        var mostCommonWordLengths = new ArrayList<Integer>();
        for (Map.Entry<Integer, Integer> entry : wordLengthCountLookup.entrySet()) {
            var wordLengthCount = entry.getValue();
            if (wordLengthCount == mostCommonWordLengthCount) {
                mostCommonWordLengths.add(entry.getKey());
            } else if (wordLengthCount > mostCommonWordLengthCount) {
                mostCommonWordLengthCount = wordLengthCount;
                mostCommonWordLengths.clear();
                mostCommonWordLengths.add(entry.getKey());
            }
        }
        double avgWordLength = 0D;
        if(wordsRead != 0) {
            avgWordLength = (double) lettersRead / wordsRead;
        }
        return new TextCountingReport(wordsRead, avgWordLength, wordLengthCountLookup,
                mostCommonWordLengthCount, mostCommonWordLengths);
    }

    private void markWordCompleted() {
        if (currentWordLength == 0) {
            return;
        }

        var currentWordLengthCount = wordLengthCountLookup.get(currentWordLength);
        // No words of this length previously found. Add a new entry.
        if (currentWordLengthCount == null) {
            wordLengthCountLookup.put(currentWordLength, 1);
        } else {
            // Increment the counter by 1
            wordLengthCountLookup.put(currentWordLength, currentWordLengthCount + 1);
        }

        wordsRead++;
        lettersRead += currentWordLength;
        currentWordLength = 0;
    }

    private boolean isPunctuation(char character) {
        for (char symbol : punctuation) {
            if (symbol == character) {
                return true;
            }
        }
        return false;
    }
}