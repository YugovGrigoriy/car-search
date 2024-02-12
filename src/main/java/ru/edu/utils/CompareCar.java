package ru.edu.utils;


import ru.edu.entity.CarEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static ru.edu.utils.ToRomanNumerals.toRoman;

public class CompareCar {


    public static List<String> compareCar(CarEntity car1, CarEntity car2) {


        List<String> result = new ArrayList<>();
        if (!(car1.getMaximumSpeed().equals(car2.getMaximumSpeed()))) {
            result.add(String.format("%s %s %s is %s faster",
                StringFormatter.capitalizeFirstLetter(compareSpeed(car1, car2).getBrand()),
                StringFormatter.capitalizeFirstLetter(compareSpeed(car1, car2).getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(compareSpeed(car1,car2).getVehicleGeneration())),
                compareTwoNumbers(Integer.parseInt(car1.getMaximumSpeed()), Integer.parseInt(car2.getMaximumSpeed()))));
        }

        if (!(car1.getPrice().equals(car2.getPrice()))) {
            result.add(String.format("%s %s %s costs %s more",
                StringFormatter.capitalizeFirstLetter(comparePrice(car1, car2).getBrand()),
                StringFormatter.capitalizeFirstLetter(comparePrice(car1, car2).getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(comparePrice(car1,car2).getVehicleGeneration())),
                compareTwoNumbers(Integer.parseInt(car1.getPrice()), Integer.parseInt(car2.getPrice()))));
        }
        if(!(car1.getFullMass().equals(car2.getFullMass()))) {
            result.add(String.format("%s %s %s weighs %s more",
                StringFormatter.capitalizeFirstLetter(compareFullMass(car1, car2).getBrand()),
                StringFormatter.capitalizeFirstLetter(compareFullMass(car1, car2).getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(compareFullMass(car1,car2).getVehicleGeneration())),
                compareTwoNumbers(Integer.parseInt(car1.getFullMass()), Integer.parseInt(car2.getFullMass()))));
        }
        return result;
    }

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
     * Calculates how much percent number 1 is greater than number 2
     *
     * @param number1 how much is this number
     * @param number2 more than that
     * @return x%
     */
    public static String compareTwoNumbers(int number1, int number2) {
        if (number1 == number2) {
            return "0%";
        }
        if (number1 == 0|| number2==0) {
            return "0%";
        }
        if (number1 <0|| number2<0) {
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
     * Find the highest speed of objects
     */
    public static CarEntity compareSpeed(CarEntity car1, CarEntity car2) {
        if(car1.getMaximumSpeed().equals("0")||car2.getMaximumSpeed().equals("0")){
            throw new InvalidParameterException("car speed cannot be 0");
         }
        int speedCar1 = Integer.parseInt(car1.getMaximumSpeed());
        int speedCar2 = Integer.parseInt(car2.getMaximumSpeed());
        if(speedCar1<1){
            throw new InvalidParameterException("The speed of the car cannot be negative, comparison is impossible!");
        }
        if(speedCar2<1){
            throw new InvalidParameterException("The speed of the car cannot be negative, comparison is impossible!");
        }
        if(speedCar1==speedCar2){
            return null;
        }
        if (speedCar1 < speedCar2) {
            return car2;
        }
        return car1;
    }

    /**
     * Find a more expensive car
     */
    public static CarEntity comparePrice(CarEntity car1, CarEntity car2) {
        if(car1.getPrice().equals("0")||car2.getPrice().equals("0")){
            throw new InvalidParameterException("car price cannot be equal to 0");
          }
        int priceCar1 = Integer.parseInt(car1.getPrice());
        int priceCar2 = Integer.parseInt(car2.getPrice());
        if(priceCar1<0){
            throw new InvalidParameterException("The price of the car cannot be negative, comparison is impossible!");
         }
        if(priceCar2<0){
            throw new InvalidParameterException("The price of the car cannot be negative, comparison is impossible!");
          }
        if(priceCar1==priceCar2){
            return null;
        }
        if (priceCar1 < priceCar2) {
            return car2;
        }
        return car1;
    }

    /**
     * Find a heavier car
     */
    public static CarEntity compareFullMass(CarEntity car1, CarEntity car2) {
        if(car1.getFullMass().equals("0")||car2.getFullMass().equals("0")){
          throw new InvalidParameterException("the mass of the car cannot be equal to 0");
         }
        int fullMassCar1 = Integer.parseInt(car1.getFullMass());
        int fullMassCar2 = Integer.parseInt(car2.getFullMass());
        if(fullMassCar1<0){
            throw new InvalidParameterException("The mass of the car cannot be negative, comparison is not possible!");
         }
        if(0>fullMassCar2){
            throw new InvalidParameterException("The mass of the car cannot be negative, comparison is not possible!");
         }
        if(fullMassCar1==fullMassCar2){
            return null;
        }
        if (fullMassCar1 < fullMassCar2) {
            return car2;
        }
        return car1;
    }


}
