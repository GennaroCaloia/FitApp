package it.gennaro.fitapp.dto.request.plan;

import jakarta.validation.constraints.Size;

public class PlanUpdateRequest {

    @Size(max=150) public String title;
    public String description;

}
