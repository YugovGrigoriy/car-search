package ru.edu;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.edu.test.MyJUnit;


public class MyJUnitTest {

    @Test
    public void testSum(){

        // arrange
        MyJUnit mj = new MyJUnit();


        // act
        int sum1 = mj.sum(0, 1);
        int sum2 = mj.sum(5, 5);
        int sum3 = mj.sum(Integer.MAX_VALUE - 5, 5);

        // assert
        Assertions.assertEquals(1, sum1);
        Assertions.assertEquals(10, sum2);
        Assertions.assertEquals(Integer.MAX_VALUE, sum3);

    }
}
