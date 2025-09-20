package it.gennaro.fitapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    public String username;

    @NotBlank
    @Email
    @Size(max = 255)
    public String email;

    @NotBlank
    @Size(min = 6, max = 128)
    public String password;
}
