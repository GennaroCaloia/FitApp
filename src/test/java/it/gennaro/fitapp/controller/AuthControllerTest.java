package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.controller.AuthController;
import it.gennaro.fitapp.security.JwtService;
import it.gennaro.fitapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtService jwt;

    @MockBean
    UserService userService;

    @Test
    void login_missing_body_is_400() throws Exception {
        mvc.perform(post("/auth/login"))
                .andExpect(status().isBadRequest());
    }
    
    

}