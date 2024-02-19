package ru.edu.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.edu.entity.CarEntity;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class AdminUtilsTest {

    @Test
    void getListUserTest() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("test");
        user.setName("test");
        List<UserEntity> test = new ArrayList<>();
        test.add(user);
        List<String> result = AdminUtils.getListUser(test);

        List<UserEntity> test1 = new ArrayList<>();
        List<UserEntity> test2 = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        test2.add(user1);
        List<String> result1 = AdminUtils.getListUser(test2);
        Assertions.assertEquals(result.get(0), "User:test ID:1 name:test");
        Assertions.assertTrue(AdminUtils.getListUser(test1).isEmpty());
        Assertions.assertEquals(result1.get(0), "User:null ID:0 name:default name");
    }

    @Test
    void getListCarTest() {
        CarEntity car = new CarEntity();
        car.setBrand("test");
        car.setModel("test");
        car.setVehicleGeneration("1");
        car.setPrice("100");
        car.setCarClass("T");
        car.setMaximumSpeed("1");
        car.setFullMass("1");
        car.setPower("1");
        car.setId(1L);
        car.setEngineCapacity("1");
        List<CarEntity> test = new ArrayList<>();
        test.add(car);
        List<String> result = AdminUtils.getListCar(test);
        List<CarEntity> test1 = new ArrayList<>();
        List<CarEntity> test2 = new ArrayList<>();
        CarEntity car1 = new CarEntity();
        test2.add(car1);
        Assertions.assertEquals(result.get(0), "1 -Test TestI -Price:100$");
        Assertions.assertTrue(AdminUtils.getListCar(test1).isEmpty());
        Assertions.assertThrows(InvalidParameterException.class, () -> AdminUtils.getListCar(test2));
    }

    @Test
    void getListReport(){
        ReportEntity report=new ReportEntity("test_date","test_name","test_message","test_email");
        report.setId(1L);
        List<ReportEntity>reports=new ArrayList<>();
        List<ReportEntity>reports1=new ArrayList<>();
        reports.add(report);
        List<String> result1=AdminUtils.getListReport(reports);
        List<String>result2=AdminUtils.getListReport(reports1);
        int quantityReports=result1.size();
        Assertions.assertEquals(result1.get(0),"Report #"+quantityReports+": id=1, report date: test_date, name = test_name, email:test_email, message:test_message");
        Assertions.assertTrue(result2.isEmpty());
    }
}
