package ru.edu.carControllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.controller.car.controller.CarController;
import ru.edu.entity.CarEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;

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
    @InjectMocks
    private CarController carController;


    @Test
    @WithMockUser
    void findCarTest() throws Exception {
        CarEntity car = new CarEntity(1L, "Test", "Test-Model", "1", "1", "100", "10", "2", "150", "1500", "C");
        when(carService.findCar(anyString(),anyString())).thenReturn(car);
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
            .andExpect(model().attribute("price", "Price: 100$"))
            .andExpect(model().attribute("model", "Test Test-model"))
           .andExpect(view().name("engine"));
    }
}
