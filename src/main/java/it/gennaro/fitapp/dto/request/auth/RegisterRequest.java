package it.gennaro.fitapp.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "RegisterRequest", description = "Request per la registrazione")
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(example = "pippo")
    public String username;

    @NotBlank
    @Email
    @Size(max = 255)
    @Schema(example = "pippo@test.it")
    public String email;

    @NotBlank
    @Size(min = 6, max = 128)
    @Schema(example = "password")
    public String password;
}
