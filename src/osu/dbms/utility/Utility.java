package osu.dbms.utility;
import java.io.IOException;
import java.util.Scanner;

public class Utility {
    // returns the first token from a line
    public static String getTokenFromLine(Scanner sc) throws IOException {
        String inputLine;
        try {
            // read a line of user's input
            inputLine = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Something went wrong with the scanner.");
            throw new IOException(e.getMessage());
        }

        // System.out.println("You entered: " + inputLine);
        String commands[] = inputLine.split(" ", 0);

        // skip if it is an empty line
        if (commands.length <= 0) {
            return null;
        }

        int idx = 0;
        while (idx < commands.length && commands[idx].isEmpty()) {
            idx = idx + 1;
        }
        if (idx >= commands.length) {
            return null;
        }
        return commands[idx];
    }

    // Reads a line and strips leading/trailing spaces.
    public static String getLineStripped(Scanner sc) throws IOException {
        String line;
        try {
            line = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Something went wrong with the scanner.");
            throw new IOException(e.getMessage());
        }

        if (line == null) {
            //System.out.println("length: 0");
            return null;
        }
        line = line.strip();
        //System.out.println("length: " + line.length());
        if (line.isBlank()) {
            return null;
        }
        return line;
    }

    //do nothing
    public static void placeholder() {
        System.out.println("Under construction!");
    }
}
