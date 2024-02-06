package ru.edu.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.edu.entity.CarEntity;



public class CompareCarTest {


    @Test
    public void compareFullMassTest() {
        CarEntity car1 = new CarEntity();
        car1.setFullMass("1000");
        CarEntity car2 = new CarEntity();
        car2.setFullMass("500");
        CarEntity car3 = new CarEntity();
        car3.setFullMass("5000");
        CarEntity car4 = new CarEntity();
        car4.setFullMass("100");
        CarEntity car5 = new CarEntity();
        car5.setFullMass("5000");
        CarEntity car6 = new CarEntity();
        car6.setFullMass("5000");
        CarEntity car7 = new CarEntity();
        car7.setFullMass("-5000");
        CarEntity car8 = new CarEntity();
        car8.setFullMass("5000");
        CarEntity car9 = new CarEntity();
        car9.setFullMass("5000");
        CarEntity car10 = new CarEntity();
        car10.setFullMass("0");
        //normal common test
        CarEntity carResult1 = CompareCar.compareFullMass(car1, car2);
        CarEntity carResult2 = CompareCar.compareFullMass(car3, car4);
        //same mass
        CarEntity carResult3 = CompareCar.compareFullMass(car5, car6);
        //negative mass
        CarEntity carResult4 = CompareCar.compareFullMass(car7, car8);
        CarEntity carResult5=CompareCar.compareFullMass(car9,car10);
        //test
        Assertions.assertEquals(carResult1, car1);
        Assertions.assertEquals(carResult2, car3);
        Assertions.assertEquals(carResult3.getFullMass(), "the mass of the cars is the same");
        Assertions.assertEquals(carResult4.getFullMass(), "The mass of the car cannot be negative, comparison is not possible!");
        Assertions.assertEquals(carResult5.getFullMass(), "the mass of the car cannot be equal to 0");
    }

    @Test
    public void comparePrice() {


    }

}
