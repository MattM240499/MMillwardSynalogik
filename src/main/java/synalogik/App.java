package synalogik;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.UnexpectedException;
import java.util.Scanner;

/**
 * Main app
 */
public class App {
    /**
     * Entry point
     */
    public static void main(String[] args) throws Exception {
        // Parse the args
        if (args.length == 0) {
            throw new IllegalArgumentException("File path argument required, but no arguments supplied.");
        }
        var filePath = args[0];
        if (filePath == null) {
            throw new IllegalArgumentException("File argument specified was null");
        }
        // Read the file, and construct a text count status object.
        var textState = new ReadTextState();
        File textFile = new File(filePath);
        try (Scanner reader = new Scanner(textFile)) {
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                textState.handleTextLine(data);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(
                    String.format("File with path %s could not be found", textFile.getAbsolutePath()));
        } catch (Exception e) {
            throw new UnexpectedException("An unexpected error occured while reading the file.");
        }

        // Finally, post process the data to produce a report and output it.
        textState.markCompleted();
        var report = textState.toReport();
        System.out.println(report.toString());
    }
}
