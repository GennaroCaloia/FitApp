package it.gennaro.fitapp.dto.request.workout.item;

import java.math.BigDecimal;

public class WorkoutItemCreateRequest {
    public Long exerciseId;
    public Integer sets;
    public Integer reps;
    public BigDecimal weight;
    public Integer restSeconds;
}
