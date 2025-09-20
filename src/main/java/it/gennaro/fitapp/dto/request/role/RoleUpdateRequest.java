package it.gennaro.fitapp.dto.request.role;

import jakarta.validation.constraints.Size;

public class RoleUpdateRequest {
    @Size(max = 255)
    public String description;
}
