package com.wgu.inventorytracker.utils;

/**
 * A helper class to allow for checking if a string is parseable to a number.
 */
public class TryParse {
    /**
     * Checks if provided string is an int.
     *
     * @param input string to parse.
     * @return a boolean indicating the string is an int or not.
     */
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
