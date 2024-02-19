package ru.edu.utils;

import ru.edu.entity.CarEntity;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
/**
 * The AdminUtils class provides utility methods for generating formatted lists of users, reports, and cars.
 */
public class AdminUtils {
    /**
     * Generates a list of formatted user information.
     *
     * @param users The list of UserEntity objects.
     * @return A list of formatted user information strings.
     */
    public static List<String>getListUser(List<UserEntity>users){
        List<String>listUsers=new ArrayList<>();
        if(users.isEmpty()){
            return new ArrayList<>();
        }
        for (UserEntity user : users) {
            listUsers.add(String.format("User:%s ID:%s name:%s",
                user.getUsername(),
                user.getId(),
                user.getName()));
        }
        return listUsers;
    }
    /**
     * Generates a list of formatted report information.
     *
     * @param reports The list of ReportEntity objects.
     * @return A list of formatted report information strings.
     */
    public static List<String>getListReport(List<ReportEntity>reports) {
        List<String> listReports = new ArrayList<>();
        for (int i = 0; i < reports.size(); i++) {
            ReportEntity report = reports.get(i);
            String r = String.format("Report #%d: id=%d, report date: %s, name = %s, email:%s, message:%s",
                i + 1,
                report.getId(),
                report.getLocalDateTime(),
                report.getName(),
                report.getEmail(),
                report.getMessage());
            listReports.add(r);
        }
        return listReports;
    }
    /**
     * Generates a list of formatted car information.
     *
     * @param cars The list of CarEntity objects.
     * @return A list of formatted car information strings.
     * @throws InvalidParameterException if the car is empty.
     */
    public static List<String>getListCar(List<CarEntity>cars){
        List<String>listCar=new ArrayList<>();
        if(cars.isEmpty()){
            return new ArrayList<>();
        }
        for (CarEntity car : cars) {
            if (car.carIsEmpty()){
                throw new InvalidParameterException();
            }
            listCar.add(String.format("%s -%s %s%s -Price:%s$",
                car.getId(),
                StringFormatter.capitalizeFirstLetter(car.getBrand()),
                StringFormatter.capitalizeFirstLetter(car.getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(car.getVehicleGeneration())),
                car.getPrice()));

        }
        return listCar;
    }
}
