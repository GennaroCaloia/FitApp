package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.controller.HelloController;
import it.gennaro.fitapp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
@AutoConfigureMockMvc(addFilters = false)
class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private JwtService jwtService;

    @Test
    void hello_returns_body() throws Exception {
        mvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fitapp")));
    }


}