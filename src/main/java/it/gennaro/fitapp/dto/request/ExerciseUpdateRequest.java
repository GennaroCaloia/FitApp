package it.gennaro.fitapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseUpdateRequest {

    @Size(max = 150)
    public String name;

    @Size(max = 100)
    public String bodyPart;

    @Size(max = 50)
    public String type;

    public Long mediaId;
}
