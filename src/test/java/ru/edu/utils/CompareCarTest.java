package ru.edu.utils;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.edu.entity.CarEntity;

import java.util.List;


public class CompareCarTest {

    CarEntity car1 = new CarEntity();
    CarEntity car2 = new CarEntity();
    CarEntity car3 = new CarEntity();
    CarEntity car4 = new CarEntity();
    CarEntity car5 = new CarEntity();
    CarEntity car6 = new CarEntity();
    CarEntity car7 = new CarEntity();
    CarEntity car8 = new CarEntity();
    CarEntity car9 = new CarEntity();
    CarEntity car10 = new CarEntity();

    @Test
    void compareFullMassTest() {
        car1.setFullMass("1000");
        car2.setFullMass("500");
        car3.setFullMass("5000");
        car4.setFullMass("100");
        car5.setFullMass("5000");
        car6.setFullMass("5000");
        car7.setFullMass("-5000");
        car8.setFullMass("5000");
        car9.setFullMass("5000");
        car10.setFullMass("0");
        //normal common test
        CarEntity carResult1 = CompareCar.compareFullMass(car1, car2);
        CarEntity carResult2 = CompareCar.compareFullMass(car3, car4);
        //same mass
        CarEntity carResult3 = CompareCar.compareFullMass(car5, car6);
        //negative mass
        CarEntity carResult4 = CompareCar.compareFullMass(car7, car8);
        CarEntity carResult5 = CompareCar.compareFullMass(car9, car10);
        //test
        Assertions.assertEquals(carResult1, car1);
        Assertions.assertEquals(carResult2, car3);
        Assertions.assertEquals(carResult3.getFullMass(), "the mass of the cars is the same");
        Assertions.assertEquals(carResult4.getFullMass(), "The mass of the car cannot be negative, comparison is not possible!");
        Assertions.assertEquals(carResult5.getFullMass(), "the mass of the car cannot be equal to 0");
    }

    @Test
    void comparePrice() {
        car1.setPrice("1000");
        car2.setPrice("500");
        car3.setPrice("5000");
        car4.setPrice("100");
        car5.setPrice("5000");
        car6.setPrice("5000");
        car7.setPrice("-5000");
        car8.setPrice("5000");
        car9.setPrice("5000");
        car10.setPrice("0");
        //normal common test
        CarEntity carResult1 = CompareCar.comparePrice(car1, car2);
        CarEntity carResult2 = CompareCar.comparePrice(car3, car4);
        //same mass
        CarEntity carResult3 = CompareCar.comparePrice(car5, car6);
        //negative mass
        CarEntity carResult4 = CompareCar.comparePrice(car7, car8);
        CarEntity carResult5 = CompareCar.comparePrice(car9, car10);
        //test
        Assertions.assertEquals(carResult1, car1);
        Assertions.assertEquals(carResult2, car3);
        Assertions.assertEquals(carResult3.getPrice(), "cars prices are the same");
        Assertions.assertEquals(carResult4.getPrice(), "The price of the car cannot be negative, comparison is impossible!");
        Assertions.assertEquals(carResult5.getPrice(), "car price cannot be equal to 0");

    }

    @Test
    void compareSpeedTest() {
        car1.setMaximumSpeed("1000");
        car2.setMaximumSpeed("500");
        car3.setMaximumSpeed("5000");
        car4.setMaximumSpeed("100");
        car5.setMaximumSpeed("5000");
        car6.setMaximumSpeed("5000");
        car7.setMaximumSpeed("-5000");
        car8.setMaximumSpeed("5000");
        car9.setMaximumSpeed("5000");
        car10.setMaximumSpeed("0");
        //normal common test
        CarEntity carResult1 = CompareCar.compareSpeed(car1, car2);
        CarEntity carResult2 = CompareCar.compareSpeed(car3, car4);
        //same mass
        CarEntity carResult3 = CompareCar.compareSpeed(car5, car6);
        //negative mass
        CarEntity carResult4 = CompareCar.compareSpeed(car7, car8);
        CarEntity carResult5 = CompareCar.compareSpeed(car9, car10);
        //test
        Assertions.assertEquals(carResult1, car1);
        Assertions.assertEquals(carResult2, car3);
        Assertions.assertEquals(carResult3.getMaximumSpeed(), "the cars speed is the same");
        Assertions.assertEquals(carResult4.getMaximumSpeed(), "The speed of the car cannot be negative, comparison is impossible!");
        Assertions.assertEquals(carResult5.getMaximumSpeed(), "car speed cannot be 0");
    }

    @Test
    void CompareTwoNumbersTest() {
        String result1 = CompareCar.compareTwoNumbers(3, 10);
        String result2 = CompareCar.compareTwoNumbers(10, 0);
        String result3 = CompareCar.compareTwoNumbers(-10, 10);
        String result4 = CompareCar.compareTwoNumbers(10, 10);

        Assertions.assertEquals(result1, "233%");
        Assertions.assertEquals(result2, "0%");
        Assertions.assertEquals(result3, "0%");
        Assertions.assertEquals(result4, "0%");

    }

    @Test
    void compareCarTest() {
        car1.setPrice("100");
        car1.setMaximumSpeed("100");
        car1.setFullMass("100");
        car2.setPrice("200");
        car2.setMaximumSpeed("200");
        car2.setFullMass("200");
        car3.setPrice("100");
        car3.setMaximumSpeed("100");
        car3.setFullMass("100");
        car4.setPrice("100");
        car4.setMaximumSpeed("100");
        car4.setFullMass("100");
        car5.setPrice("100");
        car5.setMaximumSpeed("100");
        car5.setFullMass("100");
        car6.setPrice("100");
        car6.setMaximumSpeed("100");
        car6.setFullMass("200");
        car7.setPrice("100");
        car7.setMaximumSpeed("100");
        car7.setFullMass("100");
        car8.setPrice("100");
        car8.setMaximumSpeed("200");
        car8.setFullMass("200");
        List<String> result1 = CompareCar.compareCar(car1,car2);
        List<String> result2 = CompareCar.compareCar(car3,car4);
        List<String> result3 = CompareCar.compareCar(car5,car6);
        List<String> result4 = CompareCar.compareCar(car7,car8);
        Assertions.assertEquals(result1.size(),3);
        Assertions.assertEquals(result2.size(),0);
        Assertions.assertEquals(result3.size(),1);
        Assertions.assertEquals(result4.size(),2);

    }

    @Test
    void buildCompareResultTest(){
        car1.setBrand("Ford");
        car2.setBrand("Ford");
        car1.setModel("Test");
        car2.setModel("Test");
        car1.setVehicleGeneration("1");
        car2.setVehicleGeneration("1");
        car3.setBrand("Ford");
        car4.setBrand("Ford");
        car3.setModel("Test");
        car4.setModel("Test");
        car3.setVehicleGeneration("1");
        car4.setVehicleGeneration("1");
        car1.setPrice("100");
        car1.setMaximumSpeed("100");
        car1.setFullMass("100");
        car2.setPrice("200");
        car2.setMaximumSpeed("200");
        car2.setFullMass("200");
        car3.setPrice("100");
        car3.setMaximumSpeed("100");
        car3.setFullMass("100");
        car4.setPrice("100");
        car4.setMaximumSpeed("100");
        car4.setFullMass("100");
        String result1=CompareCar.buildCompareResult(car1,car2);
        String result2=CompareCar.buildCompareResult(car3,car4);

        Assertions.assertTrue(result1.contains("Ford Test I"));
        Assertions.assertTrue(result1.length()>50);

        Assertions.assertTrue(result2.contains("Cars have no difference"));
    }

}
