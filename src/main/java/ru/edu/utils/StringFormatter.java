package ru.edu.utils;

public class StringFormatter {

    public static String capitalizeFirstLetter(String str){
        if (str == null || str.isEmpty() || Character.isDigit(str.charAt(0))) {
            return str;
        }

        String firstLetter = str.substring(0, 1).toUpperCase();
        String restOfString = str.substring(1).toLowerCase();

        return firstLetter + restOfString;
    }
}
