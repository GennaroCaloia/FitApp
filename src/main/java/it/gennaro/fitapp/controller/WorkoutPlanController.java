package it.gennaro.fitapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.gennaro.fitapp.entity.Workout;
import it.gennaro.fitapp.service.WorkoutPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans/{planId}/workouts")
@RequiredArgsConstructor
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    @Operation(summary = "Lista i workout di un plan")
    @GetMapping
    public List<Workout> list(Authentication auth, @PathVariable Long planId) {
        return workoutPlanService.listWorkouts(auth.getName(), planId);
    }

    @Operation(summary = "Aggiunge un workout a un plan")
    @PostMapping("/{workoutId}")
    public void add(Authentication auth,
                    @PathVariable Long planId,
                    @PathVariable Long workoutId) {
        workoutPlanService.addWorkout(auth.getName(), planId, workoutId);
    }

    @Operation(summary = "Rimuove un workout da un plan")
    @DeleteMapping("/{workoutId}")
    public void remove(Authentication auth,
                       @PathVariable Long planId,
                       @PathVariable Long workoutId) {
        workoutPlanService.removeWorkout(auth.getName(), planId, workoutId);
    }

}
