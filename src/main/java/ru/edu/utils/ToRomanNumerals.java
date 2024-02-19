package ru.edu.utils;
/**
 * The ToRomanNumerals class provides a utility method to convert an integer to a Roman numeral representation.
 */
public class ToRomanNumerals {
    /**
     * Converts an integer to a Roman numeral representation.
     *
     * @param number The integer to convert to a Roman numeral.
     * @return The Roman numeral representation of the input integer as a string.
     */
    public static String toRoman(int number){

        if(number<1||number>3999){
            return "";
        }
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[number/1000] + hundreds[(number%1000)/100] + tens[(number%100)/10] + ones[number%10];
    }
}
