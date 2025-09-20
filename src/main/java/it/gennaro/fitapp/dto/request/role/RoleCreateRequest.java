package it.gennaro.fitapp.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleCreateRequest {
    @NotBlank
    @Size(max=50)
    public String code;

    @Size(max=255)
    public String description;
}
