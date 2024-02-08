package ru.edu.utils;

public class StringFormatter {

    public static String capitalizeFirstLetter(String str) {
        if (str.isEmpty() || Character.isDigit(str.charAt(0))) {
            return str+"-change is not possible";
        }

        String firstLetter = str.substring(0, 1).toUpperCase();
        String restOfString = str.substring(1).toLowerCase();

        return firstLetter + restOfString;
    }

    public static String extractIdFromDescription(String description){
        int startIndex = description.indexOf("(id:") + 4;
        int endIndex = description.indexOf(")", startIndex);
        return description.substring(startIndex, endIndex);
    }


    }

