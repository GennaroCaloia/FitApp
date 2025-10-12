package it.gennaro.fitapp.dto.request.workout;

import it.gennaro.fitapp.dto.request.workout.item.WorkoutItemCreateRequest;

import java.time.OffsetDateTime;
import java.util.List;

public class WorkoutCreateRequest {
    public Long planId;                      // obbligatorio
    public OffsetDateTime startedAt;
    public OffsetDateTime finishedAt;
    public String notes;
    public List<WorkoutItemCreateRequest> items;
}
