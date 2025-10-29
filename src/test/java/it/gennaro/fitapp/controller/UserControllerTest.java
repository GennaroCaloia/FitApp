package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.controller.UserController;
import it.gennaro.fitapp.repository.UserRepository;
import it.gennaro.fitapp.mapper.UserMapper;
import it.gennaro.fitapp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserRepository users;

    @MockBean
    UserMapper mapper;

    @MockBean
    private JwtService jwtService;

    @Test
    void me_requires_authentication() throws Exception {
        mvc.perform(get("/api/users/me"))
                .andExpect(status().is4xxClientError());
    }

}