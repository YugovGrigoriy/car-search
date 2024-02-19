package ru.edu.utils;
/**
 * The StringFormatter class provides utility methods for formatting strings.
 */
public class StringFormatter {
    /**
     * Capitalizes the first letter of a string and converts the rest to lowercase.
     *
     * @param str The input string to format.
     * @return The formatted string with the first letter capitalized and the rest in lowercase.
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null||str.isEmpty() || Character.isDigit(str.charAt(0))) {
            return "";
        }

        String firstLetter = str.substring(0, 1).toUpperCase();
        String restOfString = str.substring(1).toLowerCase();

        return firstLetter + restOfString;
    }
    /**
     * Extracts an ID from a description string.
     *
     * @param description The description string containing the ID in the format "(id:123)".
     * @return The extracted ID as a string.
     */
    public static String extractIdFromDescription(String description){
        int startIndex = description.indexOf("(id:") + 4;
        int endIndex = description.indexOf(")", startIndex);
        return description.substring(startIndex, endIndex);
    }


    }

