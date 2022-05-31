package com.wgu.inventorytracker.utils;

public class TryParse {
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
