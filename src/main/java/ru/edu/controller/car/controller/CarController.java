package ru.edu.controller.car.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.entity.CarEntity;
import ru.edu.service.CarService;

import static ru.edu.utils.ToRomanNumerals.toRoman;

@Controller
@RequestMapping(value = "/car")
public class CarController {

    CarService carService;

    @GetMapping("/find")
    public String findCar(Model model,
                          @RequestParam("model") String carModel,
                          @RequestParam("vehicleGeneration") String vehicleGeneration) {
        if (carModel.isEmpty() || vehicleGeneration.isEmpty()) {
            //todo добавить логику
            return "engine";
        }
        String newCarModel = carModel.substring(carModel.indexOf(" ") + 1).toLowerCase();
        CarEntity car = carService.findCar(newCarModel, vehicleGeneration);
        String brand = car.getBrand();
        brand = Character.toUpperCase(brand.charAt(0)) + brand.substring(1);
        String carModel1 = car.getModel();
        carModel1=Character.toUpperCase(carModel1.charAt(0)) + carModel1.substring(1);
        String newVehicleGeneration="Car vehicle generation: "+toRoman(Integer.parseInt(car.getVehicleGeneration()));
        String price="Price: "+car.getPrice()+"$";
        String power="Car power: "+car.getPower()+" hp";
        String engineCapacity="Engine capacity: "+car.getEngineCapacity()+"L";
        String maximumSpeed="Max speed: "+car.getMaximumSpeed()+" km/h";
        String fullMass="Full mass: "+car.getFullMass()+" kg";
        String carClass="Class car: "+car.getCarClass();
        model.addAttribute("model", brand+" "+carModel1);
        model.addAttribute("newVehicleGeneration",newVehicleGeneration );
        model.addAttribute("price", price);
        model.addAttribute("power",power );
        model.addAttribute("engineCapacity",engineCapacity );
        model.addAttribute("maximumSpeed",maximumSpeed );
        model.addAttribute("fullMass", fullMass);
        model.addAttribute("carClass",carClass );
        model.addAttribute("img", "../image/car_picture/" + car.getPictureNumber() + ".png");
        return "engine";
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}
