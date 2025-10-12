package it.gennaro.fitapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.gennaro.fitapp.dto.request.auth.LoginRequest;
import it.gennaro.fitapp.dto.request.auth.RegisterRequest;
import it.gennaro.fitapp.dto.response.LoginResponse;
import it.gennaro.fitapp.security.JwtService;
import it.gennaro.fitapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Login e registrazione")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtService jwt, UserService userService) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.userService = userService;
    }

    @Operation(
            summary = "Login",
            description = "Autentica un utente e restituisce un JWT (Bearer).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "demo", value =
                                    """
                                        { "username": "demo", "password": "demo" }
                                    """)
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = @ExampleObject(value = """
                                             { "accessToken": "eyJhbGciOiJIUzI1NiIs...", "expiresAt": "2025-09-08T20:15:30Z" }
                                             """))),
                    @ApiResponse(responseCode = "401", description = "Credenziali errate")
            }
    )
    @PostMapping("/login")
    public LoginResponse login (@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwt.generateToken((UserDetails) auth.getPrincipal());

        return new LoginResponse(token, Instant.now());
    }

    @Operation(
            summary = "Registrazione",
            description = "Registra un utente e restituisce un JWT (Bearer).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "demo", value =
                                            """
                                                { "username": "demo", "email": "demo@test.it", "password": "demo" }
                                            """)
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = @ExampleObject(value = """
                                             { "accessToken": "eyJhbGciOiJIUzI1NiIs...", "expiresAt": "2025-09-08T20:15:30Z" }
                                             """))),
                    @ApiResponse(responseCode = "401", description = "Credenziali errate")
            }
    )
    @PostMapping("/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest req) {
        var user = userService.register(req.username, req.email, req.password);

        // Dopo la registrazione, autentichiamo subito l’utente e rilasciamo un token
        var authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getCode()))
                .toList(); // Restituisce una List<SimpleGrantedAuthority>

        var springUser = User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .build();

        String token = jwt.generateToken(springUser);
        return new LoginResponse(token, Instant.now());
    }


}
