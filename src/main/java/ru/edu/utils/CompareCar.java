package ru.edu.utils;


import ru.edu.entity.CarEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static ru.edu.utils.ToRomanNumerals.toRoman;
/**
 * The CompareCar class provides utility methods for comparing two car entities based on speed, price, and full mass.
 */
public class CompareCar {

    /**
     * Compares two car entities and generates a list of comparison results.
     *
     * @param car1 The first CarEntity to compare.
     * @param car2 The second CarEntity to compare.
     * @return A list of comparison results as formatted strings.
     */
    public static List<String> compareCar(CarEntity car1, CarEntity car2) {


        List<String> result = new ArrayList<>();
        CarEntity carsCompareSpeed = compareSpeed(car1, car2);
        if (carsCompareSpeed != null) {
            result.add(String.format("%s %s %s is %s faster",
                StringFormatter.capitalizeFirstLetter(carsCompareSpeed.getBrand()),
                StringFormatter.capitalizeFirstLetter(carsCompareSpeed.getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(carsCompareSpeed.getVehicleGeneration())),
                compareTwoNumbers(Integer.parseInt(car1.getMaximumSpeed()), Integer.parseInt(car2.getMaximumSpeed()))));
        }
        CarEntity carsComparePrice = comparePrice(car1, car2);
        if (carsComparePrice != null) {
            result.add(String.format("%s %s %s costs %s more",
                StringFormatter.capitalizeFirstLetter(carsComparePrice.getBrand()),
                StringFormatter.capitalizeFirstLetter(carsComparePrice.getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(carsComparePrice.getVehicleGeneration())),
                compareTwoNumbers(Integer.parseInt(car1.getPrice()), Integer.parseInt(car2.getPrice()))));
        }
        CarEntity carCompareFullMass = compareFullMass(car1, car2);
        if (carCompareFullMass != null) {
            result.add(String.format("%s %s %s weighs %s more",
                StringFormatter.capitalizeFirstLetter(carCompareFullMass.getBrand()),
                StringFormatter.capitalizeFirstLetter(carCompareFullMass.getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(carCompareFullMass.getVehicleGeneration())),
                compareTwoNumbers(Integer.parseInt(car1.getFullMass()), Integer.parseInt(car2.getFullMass()))));
        }
        return result;
    }
    /**
     * Builds a formatted comparison result for two car entities.
     *
     * @param car1 The first CarEntity to compare.
     * @param car2 The second CarEntity to compare.
     * @return A formatted comparison result as a string.
     */
    public static String buildCompareResult(CarEntity car1, CarEntity car2) {
        List<String> arg = CompareCar.compareCar(car1, car2);
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Comparing two cars:</h2> <br> ");
        sb.append(String.format("%s %s %s",
            StringFormatter.capitalizeFirstLetter(car1.getBrand()),
            StringFormatter.capitalizeFirstLetter(car1.getModel()),
            toRoman(Integer.parseInt(car1.getVehicleGeneration()))));
        sb.append("<br>");
        sb.append(String.format("%s %s %s",
            StringFormatter.capitalizeFirstLetter(car2.getBrand()),
            StringFormatter.capitalizeFirstLetter(car2.getModel()),
            toRoman(Integer.parseInt(car2.getVehicleGeneration()))));
        sb.append("<br>");

        if (arg.isEmpty()) {
            sb.append("Cars have no difference");
        }

        for (String s : arg) {
            sb.append(s);
            sb.append("<br>");
        }

        return sb.toString();
    }
    /**
     * Compares two numbers and calculates the percentage difference.
     *
     * @param number1 The first number to compare.
     * @param number2 The second number to compare.
     * @return The percentage difference between the two numbers as a string.
     */
    public static String compareTwoNumbers(int number1, int number2) {
        if (number1 == number2) {
            return "0%";
        }
        if (number1 == 0 || number2 == 0) {
            return "0%";
        }
        if (number1 < 0 || number2 < 0) {
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
        return percentageDifference + "%";
    }
    /**
     * Compares the maximum speed of two cars and returns the faster car.
     *
     * @param car1 The first CarEntity to compare speed.
     * @param car2 The second CarEntity to compare speed.
     * @return The faster CarEntity, or null if speeds are equal.
     * @throws InvalidParameterException if speed is 0 or negative.
     */
    public static CarEntity compareSpeed(CarEntity car1, CarEntity car2) {
        if (car1.getMaximumSpeed().equals("0") || car2.getMaximumSpeed().equals("0")) {
            throw new InvalidParameterException("car speed cannot be 0");
        }
        int speedCar1 = Integer.parseInt(car1.getMaximumSpeed());
        int speedCar2 = Integer.parseInt(car2.getMaximumSpeed());
        if (speedCar1 < 1) {
            throw new InvalidParameterException("The speed of the car cannot be negative, comparison is impossible!");
        }
        if (speedCar2 < 1) {
            throw new InvalidParameterException("The speed of the car cannot be negative, comparison is impossible!");
        }
        if (speedCar1 == speedCar2) {
            return null;
        }
        if (speedCar1 < speedCar2) {
            return car2;
        }
        return car1;
    }
    /**
     * Compares the price of two cars and returns the more expensive car.
     *
     * @param car1 The first CarEntity to compare price.
     * @param car2 The second CarEntity to compare price.
     * @return The more expensive CarEntity, or null if prices are equal.
     * @throws InvalidParameterException if price is 0 or negative.
     */
    public static CarEntity comparePrice(CarEntity car1, CarEntity car2) {
        if (car1.getPrice().equals("0") || car2.getPrice().equals("0")) {
            throw new InvalidParameterException("car price cannot be equal to 0");
        }
        int priceCar1 = Integer.parseInt(car1.getPrice());
        int priceCar2 = Integer.parseInt(car2.getPrice());
        if (priceCar1 < 0) {
            throw new InvalidParameterException("The price of the car cannot be negative, comparison is impossible!");
        }
        if (priceCar2 < 0) {
            throw new InvalidParameterException("The price of the car cannot be negative, comparison is impossible!");
        }
        if (priceCar1 == priceCar2) {
            return null;
        }
        if (priceCar1 < priceCar2) {
            return car2;
        }
        return car1;
    }


    /**
     * Compares the full mass of two cars and returns the heavier car.
     *
     * @param car1 The first CarEntity to compare full mass.
     * @param car2 The second CarEntity to compare full mass.
     * @return The heavier CarEntity, or null if full masses are equal.
     * @throws InvalidParameterException if full mass is 0 or negative.
     */
    public static CarEntity compareFullMass(CarEntity car1, CarEntity car2) {
        if (car1.getFullMass().equals("0") || car2.getFullMass().equals("0")) {
            throw new InvalidParameterException("the mass of the car cannot be equal to 0");
        }
        int fullMassCar1 = Integer.parseInt(car1.getFullMass());
        int fullMassCar2 = Integer.parseInt(car2.getFullMass());
        if (fullMassCar1 < 0) {
            throw new InvalidParameterException("The mass of the car cannot be negative, comparison is not possible!");
        }
        if (0 > fullMassCar2) {
            throw new InvalidParameterException("The mass of the car cannot be negative, comparison is not possible!");
        }
        if (fullMassCar1 == fullMassCar2) {
            return null;
        }
        if (fullMassCar1 < fullMassCar2) {
            return car2;
        }
        return car1;
    }


}
