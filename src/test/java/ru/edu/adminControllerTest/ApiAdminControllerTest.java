package ru.edu.adminControllerTest;

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
import ru.edu.controller.admin.controller.ApiAdminController;
import ru.edu.entity.CarEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@WebMvcTest({ApiAdminController.class})
public class ApiAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CarService carService;
    private static AutoCloseable closeable;
    @InjectMocks
    private ApiAdminController apiAdminController;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiAdminController).build();

    }

    @AfterAll
    static void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    @WithMockUser("testAdminUser")
    void updateCarPriceTest()throws Exception {
        CarEntity car = new CarEntity(1L, "Test", "Test-Model", "1", "1", "100", "2", "150", "1500", "C");
        doNothing().when(carService).updatePriceCar(1L, 100);
        when(carService.findCar("1")).thenReturn(car);
        mockMvc.perform(post("/admin/api/update-car-price")
            .param("carId","1")
                .param("newPrice","0"))
            .andExpect(redirectedUrl("/admin/update?carNotFound=price can not be 0"));

        mockMvc.perform(post("/admin/api/update-car-price")
                .param("carId","0")
                .param("newPrice","100"))
            .andExpect(redirectedUrl("/admin/update?carNotFound=car ID can not be 0"));

        mockMvc.perform(post("/admin/api/update-car-price")
                .param("carId","1")
                .param("newPrice","100"))
            .andExpect(redirectedUrl("/admin/admin-dashboard"));

        mockMvc.perform(post("/admin/api/update-car-price")
                .param("carId","12")
                .param("newPrice","100"))
            .andExpect(redirectedUrl("/admin/update?carNotFound=car with id:12 not found"));
    }

    @Test
    @WithMockUser("testAdminUser")
    void unBlockUserTest()throws Exception{
        doNothing().when(userService).changeRole(anyString(),anyLong());
        mockMvc.perform(post("/admin/api/add-user/unBlock")
               .param("ID","100"))
            .andExpect(redirectedUrl("/admin/blocked-user"));

        mockMvc.perform(post("/admin/api/add-user/unBlock")
                .param("ID","0"))
            .andExpect(redirectedUrl("/admin/blocked-user"));
    }

    @Test
    @WithMockUser("testAdminUser")
    void BlockUserTest()throws Exception{
        doNothing().when(userService).changeRole(anyString(),anyLong());
        mockMvc.perform(post("/admin/api/add-user/blockList")
                .param("ID","100"))
            .andExpect(redirectedUrl("/admin/blocked-user"));

        mockMvc.perform(post("/admin/api/add-user/unBlock")
                .param("ID","0"))
            .andExpect(redirectedUrl("/admin/blocked-user"));
    }
}
