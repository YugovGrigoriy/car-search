package ru.edu.userControllerTest;



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.controller.user.controller.UserController;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.UserService;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)

public class UserControllerTest {
    private MockMvc mockMvc;
    private static AutoCloseable closeable;
    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @AfterAll
    public static void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    public void welcomePageTest() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("engine"));
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-in"));
    }

    @Test
    public void reportTest() throws Exception {
        mockMvc.perform(get("/report"))
            .andExpect(status().isOk())
            .andExpect(view().name("user-message"));
    }

    @Test
    public void helpTest() throws Exception {
        mockMvc.perform(get("/help"))
            .andExpect(status().isOk())
            .andExpect(view().name("helpRU"));
        mockMvc.perform(get("/helpEU"))
            .andExpect(status().isOk())
            .andExpect(view().name("helpENG"));
    }

    @Test
    void signUpFormTest() throws Exception {
        String newUserName = "test";
        String newUserName1 = "";

        mockMvc.perform(get("/registration"))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-up"))
            .andExpect(model().size(0));

        mockMvc.perform(get("/registration")
                .param("registrationStatus", "failed")
                .param("newUsername", newUserName))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-up"))
            .andExpect(model().size(1))
            .andExpect(model().attribute("registrationFailed", newUserName));

        mockMvc.perform(get("/registration")
                .param("registrationStatus", "failed")
                .param("newUsername", newUserName1))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-up"))
            .andExpect(model().size(1))
            .andExpect(model().attribute("registrationFailed", newUserName1));

        mockMvc.perform(get("/registration")
                .param("registrationStatus", "test")
                .param("newUsername", newUserName))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-up"))
            .andExpect(model().size(0))
            .andExpect(model().attributeDoesNotExist("registrationFailed"));

        mockMvc.perform(get("/registration")
                .param("registrationStatus", "failed"))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-up"))
            .andExpect(model().size(1))
            .andExpect(model().attributeDoesNotExist("registrationFailed"));
    }

    @Test
    void createAccountTest() throws Exception {
        String username = "test";

        mockMvc.perform(get("/create/account"))
            .andExpect(status().isBadRequest());


        mockMvc.perform(get("/create/account")
                .param("username", username))
            .andExpect(status().isOk())
            .andExpect(view().name("personal-data"))
            .andExpect(model().size(1))
            .andExpect(model().attribute("username", "test"));


    }

    @Test
    @WithMockUser("testUser")
    void personalAreaTest() throws Exception {
        UserEntity user = getUserEntity();
        when(userService.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(get("/engine/me").with(user("testUser")))
            .andExpect(status().isOk())
            .andExpect(model().size(4))
            .andExpect(model().attribute("userRole", "TEST_ROLE"))
            .andExpect(model().attribute("name", "test"))
            .andExpect(model().attribute("age", 1))
            .andExpect(model().attributeExists("favoritePosition"))

            .andDo(result -> {
                ModelAndView modelAndView = result.getModelAndView();
                if (modelAndView != null) {
                    @SuppressWarnings("unchecked")
                    List<String> favoritePosition = (List<String>) modelAndView.getModel().get("favoritePosition");
                    Assertions.assertEquals(2, favoritePosition.size());
                    Assertions.assertEquals("1: Test-brand Test-model I (id:123)", favoritePosition.get(0));
                    Assertions.assertEquals("2: Test-brand1 Test-model1 II (id:321)", favoritePosition.get(1));
                }
            })

            .andExpect(view().name("personal-area"));


    }

    private UserEntity getUserEntity() {
        CarEntity car = new CarEntity();
        car.setId(123L);
        car.setBrand("Test-Brand");
        car.setModel("Test-Model");
        car.setVehicleGeneration("1");
        car.setPictureNumber("1");
        car.setPrice("100");
        car.setPower("200");
        car.setEngineCapacity("100");
        car.setMaximumSpeed("100");
        car.setFullMass("50");
        car.setCarClass("T");

        CarEntity car1 = new CarEntity();
        car1.setId(321L);
        car1.setBrand("Test-Brand1");
        car1.setModel("Test-Model1");
        car1.setVehicleGeneration("2");
        car1.setPictureNumber("2");
        car1.setPrice("200");
        car1.setPower("300");
        car1.setEngineCapacity("200");
        car1.setMaximumSpeed("200");
        car1.setFullMass("10");
        car1.setCarClass("D");

        UserEntity user = new UserEntity();
        user.setId(123);
        user.setUsername("testUser");
        user.setName("test");
        user.setRole("TEST_ROLE");
        user.setAge(1);
        List<CarEntity> favoriteCars = new ArrayList<>();
        favoriteCars.add(car);
        favoriteCars.add(car1);
        user.setFavoriteCars(favoriteCars);
        return user;
    }
}
