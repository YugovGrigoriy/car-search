package ru.edu.utils;

import ru.edu.entity.CarEntity;

public class CompareCar {

    public static String compareCar(CarEntity car1, CarEntity car2) {
        String model1 = car1.getModel();
        String model2 = car2.getModel();
        return "comparing two cars: <br> " +
            "Ford " + model1 + "<br>" +
            "Ford " + model2 + "<br>" +
            "one can conclude: <br>" +
            "1.Ford " + compareSpeed(car1, car2).getModel() + " is "
            + CompareTwoComponent(Integer.parseInt(car1.getMaximumSpeed()), Integer.parseInt(car2.getMaximumSpeed()))
            + " faster";
    }

    private static String CompareTwoComponent(int number1,int number2) {
        int percentageFaster = (int) Math.round(((double) (number1 - number2) / number2) * 100);
        return percentageFaster + "%";
    }


    private static CarEntity compareSpeed(CarEntity car1, CarEntity car2) {
        int speedCar1 = Integer.parseInt(car1.getMaximumSpeed());
        int speedCar2 = Integer.parseInt(car2.getMaximumSpeed());
        if (speedCar1 < speedCar2) {
            return car2;
        }
        return car1;
    }


}
