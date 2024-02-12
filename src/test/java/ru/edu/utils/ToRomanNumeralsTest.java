package ru.edu.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToRomanNumeralsTest {
    @Test
    void toRomanTest(){
        String result1=ToRomanNumerals.toRoman(0);
        String result2=ToRomanNumerals.toRoman(10);
        String result3=ToRomanNumerals.toRoman(-10);
        String result4=ToRomanNumerals.toRoman(3999);
        String result5=ToRomanNumerals.toRoman(Integer.MAX_VALUE);
        Assertions.assertEquals(result1,"");
        Assertions.assertEquals(result2,"X");
        Assertions.assertEquals(result3,"");
        Assertions.assertEquals(result4,"MMMCMXCIX");
        Assertions.assertEquals(result5,"");
    }
}
