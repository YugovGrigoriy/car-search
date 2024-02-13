package ru.edu.userControllerTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.edu.controller.user.controller.ApiUserController;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ApiUserController.class})
public class ApiUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static AutoCloseable closeable;

    @MockBean
    private UserService userService;
    @MockBean
    private ReportService reportService;

    @InjectMocks
    private ApiUserController apiUserController;


    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiUserController).build();

    }

    @AfterAll
    static void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void signUpFormTest() throws Exception {
        when(userService.saveUser(any(UserEntity.class))).thenAnswer(invocationOnMock -> {
            UserEntity user = invocationOnMock.getArgument(0);
            if (user.getUsername().equals("test")) {
                return true;
            } else {
                return false;
            }
        });
        when(userService.generateLogin(any())).thenReturn("newLogin");
        mockMvc.perform(post("/api/create?username=test&password=123"))
            .andExpect(redirectedUrl("/create/account?userName=test"));
        mockMvc.perform(post("/api/create?username=duplicate-name&password=123"))
            .andExpect(redirectedUrl("/registration?registrationStatus=failed&newUsername=newLogin"));
        mockMvc.perform(post("/api/create"))
            .andExpect(redirectedUrl("/registration"));


    }

    @Test
    void fillPersonDateTest() throws Exception {
        doNothing().when(userService).updateUser(any(UserEntity.class));
        UserEntity user = new UserEntity();
        user.setUsername("test");
        when(userService.findByUsername(anyString())).thenReturn(user);
        mockMvc.perform(post("/api/user/me")
                .param("username", user.getUsername())
                .param("age", "10")
                .param("name", "newTestName"))
            .andExpect(model().size(0))
            .andExpect(redirectedUrl("/login"));

        mockMvc.perform(post("/api/user/me")
                .param("username", user.getUsername())
                .param("age", "1000")
                .param("name", "newTestName"))
            .andExpect(model().size(2))
            .andExpect(view().name("personal-data"));

        mockMvc.perform(post("/api/user/me")
                .param("username", "")
                .param("age", "10")
                .param("name", "newTestName"))
            .andExpect(model().size(0))
            .andExpect(redirectedUrl("/login"));

    }

    @Test
    void reportTest() throws Exception {
        doNothing().when(reportService).save(any(ReportEntity.class));
        mockMvc.perform(post("/api/report")
                .param("name", "test")
                .param("message", "test")
                .param("email", "test"))
            .andExpect(redirectedUrl("/"));
    }



}
