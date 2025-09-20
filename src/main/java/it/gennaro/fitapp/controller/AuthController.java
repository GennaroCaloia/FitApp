package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.dto.request.LoginRequest;
import it.gennaro.fitapp.dto.request.RegisterRequest;
import it.gennaro.fitapp.dto.response.LoginResponse;
import it.gennaro.fitapp.security.JwtService;
import it.gennaro.fitapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtService jwt, UserService userService) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login (@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwt.generateToken((UserDetails) auth.getPrincipal());

        return new LoginResponse(token, Instant.now());
    }

    @PostMapping("/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest req) {
        var user = userService.register(req.username, req.email, req.password);
        // Dopo la registrazione, autentichiamo subito l’utente e rilasciamo un token
        var springUser = User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities((GrantedAuthority) user.getRoles().stream().map(r -> "ROLE_" + r.getCode()).toList())
                .build();
        String token = jwt.generateToken(springUser);
        return new LoginResponse(token, java.time.Instant.now());
    }


}
