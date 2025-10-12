package it.gennaro.fitapp.dto;

import java.math.BigDecimal;

public class WorkoutItemDto {
    public Long id;
    public Long exerciseId;
    public Integer sets;
    public Integer reps;
    public BigDecimal weight;
    public Integer restSeconds;
}
