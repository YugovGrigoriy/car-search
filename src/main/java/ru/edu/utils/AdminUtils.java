package ru.edu.utils;

import ru.edu.entity.CarEntity;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class AdminUtils {

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
