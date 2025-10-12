package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.dto.WorkoutDto;
import it.gennaro.fitapp.dto.request.workout.WorkoutCreateRequest;
import it.gennaro.fitapp.entity.Workout;
import it.gennaro.fitapp.mapper.WorkoutMapper;
import it.gennaro.fitapp.service.WorkoutService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;
    private final WorkoutMapper workoutMapper;

    public WorkoutController(WorkoutService workoutService, WorkoutMapper workoutMapper) {
        this.workoutService = workoutService;
        this.workoutMapper = workoutMapper;
    }

    // Restituisce gli ultimi workout dell'utente loggato
    @GetMapping("/latest")
    public Page<WorkoutDto> latest(Authentication auth,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return workoutService.latest(auth.getName(), page, size).map(workoutMapper::toDto);
    }

    @GetMapping("/{id}")
    public WorkoutDto detail(Authentication auth, @PathVariable Long id) {
        return workoutMapper.toDto(workoutService.detail(auth.getName(), id));
    }

    @PostMapping
    public ResponseEntity<WorkoutDto> create(Authentication auth, @RequestBody WorkoutCreateRequest req) {
        Workout w = workoutService.create(auth.getName(), req.planId, ww -> {
            ww.setStartedAt(req.startedAt != null ? req.startedAt : java.time.OffsetDateTime.now());
            ww.setFinishedAt(req.finishedAt);
            ww.setNotes(req.notes);
            if (req.items != null) {
                req.items.forEach(i -> workoutService.addItem(ww, i.exerciseId, i.sets, i.reps, i.weight, i.restSeconds));
            }
        });
        return ResponseEntity.ok(workoutMapper.toDto(w));
    }
}
