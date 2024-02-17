package ru.edu.carControllerTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.edu.controller.car.controller.ApiCarController;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({ApiCarController.class})
public class ApiCarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CarService carService;
    private static AutoCloseable closeable;
    @InjectMocks
    private ApiCarController apiCarController;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiCarController).build();

    }

    @AfterAll
    static void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }


    @Test
    @WithMockUser("testUser")
    void addFavoriteCarTest() throws Exception {
        UserEntity user = new UserEntity(1, "testUser", "test", 1, "", "USER", new ArrayList<>());
        CarEntity car = new CarEntity(1L, "Test", "Test-Model", "1", "1", "100", "10", "2", "150", "1500", "C");
        when(userService.findByUsername("testUser")).thenReturn(user);
        doNothing().when(userService).updateUser(any(UserEntity.class));
        when(carService.findCar(anyString())).thenReturn(car);

        mockMvc.perform(post("/api/car/add/favorite").param("idCar", ""))
            .andExpect(redirectedUrl("/"));

       mockMvc.perform(post("/api/car/add/favorite").param("idCar", "testID"))
            .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser("testUser")
    void clearTest() throws Exception {
        UserEntity user = new UserEntity(1, "testUser", "test", 1, "", "USER", new ArrayList<>());
        when(userService.findByUsername("testUser")).thenReturn(user);
        doNothing().when(userService).updateUser(any(UserEntity.class));
        mockMvc.perform(post("/api/car/clear"))
            .andExpect(redirectedUrl("/engine/me"));

    }
}
