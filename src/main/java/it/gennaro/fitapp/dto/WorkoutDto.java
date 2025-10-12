package it.gennaro.fitapp.dto;

import java.time.OffsetDateTime;
import java.util.List;

public class WorkoutDto {
    public Long id;
    public Long planId;
    public OffsetDateTime startedAt;
    public OffsetDateTime finishedAt;
    public String notes;
    public List<WorkoutItemDto> items;
}
