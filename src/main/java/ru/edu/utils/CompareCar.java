package ru.edu.utils;


import ru.edu.entity.CarEntity;

import static ru.edu.utils.ToRomanNumerals.toRoman;

public class CompareCar {
    /**
     * Get a string to provide it on the html page
     *
     * @param car1 object to compare
     * @param car2 object to compare
     * @return "string"
     */
    public static String compareCar(CarEntity car1, CarEntity car2) {
        String model1 = car1.getModel();
        String model2 = car2.getModel();
        return "<h2>Comparing two cars:</h2> <br> " +
            "Ford " + model1 + " " + toRoman(Integer.parseInt(car1.getVehicleGeneration())) + "<br>" +
            "Ford " + model2 + " " + toRoman(Integer.parseInt(car2.getVehicleGeneration())) + "<br>" +
            "one can conclude: <br>" +
            "1.Ford " + compareSpeed(car1, car2).getModel() + " is "
            + CompareTwoNumbers(Integer.parseInt(car1.getMaximumSpeed()), Integer.parseInt(car2.getMaximumSpeed()))
            + " faster" + "<br>" +
            "2. Ford " + comparePrice(car1, car2).getModel() + " costs " + CompareTwoNumbers(Integer.parseInt(car1.getPrice()), Integer.parseInt(car2.getPrice())) +
            " more" + "<br>" +
            "3. Ford " + compareFullMass(car1, car2).getModel() + " weighs " + CompareTwoNumbers(Integer.parseInt(car1.getFullMass()), Integer.parseInt(car2.getFullMass())) +
            " more";
    }

    /**
     * Calculates how much percent number 1 is greater than number 2
     *
     * @param number1 how much is this number
     * @param number2 more than that
     * @return x%
     */
    private static String CompareTwoNumbers(int number1, int number2) {
        if (number1 == number2) {
            return "0%";
        }

        int numerator, denominator;
        if (number1 > number2) {
            numerator = number1;
            denominator = number2;
        } else {
            numerator = number2;
            denominator = number1;
        }

        int percentageDifference = (int) (Math.round(((double) numerator / denominator) * 100 - 100));
        return percentageDifference + "% ";
    }

    /**
     * Find the highest speed of objects
     */
    private static CarEntity compareSpeed(CarEntity car1, CarEntity car2) {
        int speedCar1 = Integer.parseInt(car1.getMaximumSpeed());
        int speedCar2 = Integer.parseInt(car2.getMaximumSpeed());
        if (speedCar1 < speedCar2) {
            return car2;
        }
        return car1;
    }

    /**
     * Find a more expensive car
     */
    private static CarEntity comparePrice(CarEntity car1, CarEntity car2) {
        int priceCar1 = Integer.parseInt(car1.getPrice());
        int priceCar2 = Integer.parseInt(car2.getPrice());
        if (priceCar1 < priceCar2) {
            return car2;
        }
        return car1;
    }

    /**
     * Find a heavier car
     */
    private static CarEntity compareFullMass(CarEntity car1, CarEntity car2) {
        int fullMassCar1 = Integer.parseInt(car1.getFullMass());
        int fullMassCar2 = Integer.parseInt(car2.getFullMass());
        if (fullMassCar1 < fullMassCar2) {
            return car2;
        }
        return car1;
    }


}
