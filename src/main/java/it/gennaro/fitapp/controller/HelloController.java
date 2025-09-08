package it.gennaro.fitapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello Fitapp";
    }

    @GetMapping("/api/me")
    public String me (Authentication auth) {
        return "Hello, " + auth.getName();
    }
}
