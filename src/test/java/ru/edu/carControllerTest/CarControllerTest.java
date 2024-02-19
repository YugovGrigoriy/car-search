package ru.edu.carControllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.controller.car.controller.CarController;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({CarController.class})
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CarService carService;



    @Test
    @WithMockUser
    void findCarTest() throws Exception {
        CarEntity car = new CarEntity(1L, "Test", "Test-Model", "1", "1", "100", "10", "150", "1500", "C");
        when(carService.findCar(anyString(), anyString())).thenReturn(car);
        mockMvc.perform(get("/car/find").param("model", "")
                .param("vehicleGeneration", ""))
            .andExpect(status().isOk())
            .andExpect(model().size(1))
            .andExpect(model().attribute("error", "Error: Car model and generation are required"))
            .andExpect(view().name("engine"));

        mockMvc.perform(get("/car/find")
                .param("model", "")
                .param("vehicleGeneration", "")
                .with(anonymous()))
            .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/car/find").param("model", "test")
                .param("vehicleGeneration", "1"))
            .andExpect(status().isOk())
            .andExpect(model().size(10))
            .andExpect(model().attribute("price", "Price: 1$"))
            .andExpect(model().attribute("model", "Test Test-model I"))
            .andExpect(view().name("engine"));
    }

    @Test
    @WithMockUser("testUser")
    void compareTest() throws Exception {
        UserEntity user = new UserEntity(1, "testUser", "test", 1, "", "USER", new ArrayList<>());
        when(userService.findByUsername("testUser")).thenReturn(user);
        CarEntity car = new CarEntity(1L, "Test", "Test-Model", "1", "1", "100", "10", "150", "1500", "C");
        CarEntity car1 = new CarEntity(2L, "Test1", "Test-Model1", "11", "11", "1001", "101",  "1501", "15001", "D");
        when(carService.findCar("1")).thenReturn(car);
        when(carService.findCar("2")).thenReturn(car1);

        mockMvc.perform(get("/car/compare")
                .param("position1" ,"")
                .param("position2",""))
            .andExpect(status().isOk())
            .andExpect(model().size(0))
            .andExpect(view().name("personal-area"));

        mockMvc.perform(get("/car/compare")
            .param("position1" ,"test")
            .param("position2","test")
            .with(anonymous()))
            .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/car/compare")
                .param("position1" ,"(id:1)")
                .param("position2","(id:2)"))
            .andExpect(status().isOk())
            .andExpect(model().size(3))
            .andExpect(view().name("personal-area"));
    }
}
