package it.gennaro.fitapp.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserUpdateRequest {
    @Size(min=3, max=100)
    public String username;

    @Email
    @Size(max=255)
    public String email;

    public Boolean enabled;

    public Set<String> roles;
}
