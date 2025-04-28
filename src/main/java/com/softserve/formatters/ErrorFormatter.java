package com.softserve.formatters;

public class ErrorFormatter {

    private ErrorFormatter() {
    }

    /**
     * Formats an error message in a styled box to match application UI.
     *
     * @param message The error message to format
     * @return A formatted string containing the error message
     */
    public static String formatError(String message) {
        StringBuilder result = new StringBuilder();
        result.append("--------------------------------\n");
        result.append("│        ⚠️  ERROR             │\n");
        result.append("--------------------------------\n");

        int remainingWidth = 28;
        StringBuilder currentLine = new StringBuilder("│ ");

        for (String word : message.split(" ")) {
            if (word.length() + (currentLine.length() > 2 ? 1 : 0) > remainingWidth) {
                while (currentLine.length() < 31) {
                    currentLine.append(" ");
                }
                currentLine.append("│");
                result.append(currentLine).append("\n");

                currentLine = new StringBuilder("│ ");
                remainingWidth = 28;
            }

            if (currentLine.length() > 2) {
                currentLine.append(" ");
                remainingWidth--;
            }

            currentLine.append(word);
            remainingWidth -= word.length();
        }

        if (currentLine.length() > 2) {
            while (currentLine.length() < 31) {
                currentLine.append(" ");
            }
            currentLine.append("│");
            result.append(currentLine).append("\n");
        }

        result.append("--------------------------------");
        return result.toString();
    }

    /**
     * Displays an error message to standard error output with consistent formatting.
     * Ensures proper flushing of output streams to maintain correct display order.
     *
     * @param message The error message to display
     */
    public static void displayError(String message) {
        System.out.flush();
        System.err.println();

        System.err.println(formatError(message));

        System.err.flush();
    }


}
