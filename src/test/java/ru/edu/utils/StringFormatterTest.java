package ru.edu.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringFormatterTest {

    @Test
    void capitalizeFirstLetterTest(){
        String result1=StringFormatter.capitalizeFirstLetter("");
        String result2=StringFormatter.capitalizeFirstLetter("6Hello");
        String result3=StringFormatter.capitalizeFirstLetter("Hello6");
        String result4=StringFormatter.capitalizeFirstLetter("hello6");
        String result5=StringFormatter.capitalizeFirstLetter("hello world");

        Assertions.assertEquals(result1,"-change is not possible");
        Assertions.assertEquals(result2,"6Hello-change is not possible");
        Assertions.assertEquals(result3,"Hello6");
        Assertions.assertEquals(result4,"Hello6");
        Assertions.assertEquals(result5,"Hello world");
    }

    @Test
    void extractIdFromDescriptionTest(){
        String result1=StringFormatter.extractIdFromDescription("1: Ford Focus III (id:2)");
        String result2=StringFormatter.extractIdFromDescription("2: Ford Mondeo V (id:6)");
        String result3=StringFormatter.extractIdFromDescription("3: Ford Mustang VII (id:20)");

        Assertions.assertEquals(result1,"2");
        Assertions.assertEquals(result2,"6");
        Assertions.assertEquals(result3,"20");
    }
}
