package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.dto.ExerciseDto;
import it.gennaro.fitapp.dto.request.exercise.ExerciseCreateRequest;
import it.gennaro.fitapp.dto.request.exercise.ExerciseUpdateRequest;
import it.gennaro.fitapp.entity.Exercise;
import it.gennaro.fitapp.mapper.ExerciseMapper;
import it.gennaro.fitapp.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    public ExerciseController(ExerciseService service, ExerciseMapper mapper) {
        this.exerciseService = service;
        this.exerciseMapper = mapper;
    }

    @GetMapping
    public Page<ExerciseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, name = "bodyPart") String bodyPart,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 100), Sort.by("id").descending());
        return exerciseService.search(name, bodyPart, pageable).map(exerciseMapper::toDto);
    }

    @GetMapping("/{id}")
    public ExerciseDto getExercise(@PathVariable Long id) {
        return exerciseMapper.toDto(exerciseService.getExercise(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@Valid @RequestBody ExerciseCreateRequest request) {
        Exercise exerciseToSave = exerciseService.create(exerciseMapper.toEntity(request));
        return ResponseEntity.ok(exerciseMapper.toDto(exerciseToSave));
    }

    @PatchMapping("/{id}")
    public ExerciseDto updateExercise(@PathVariable Long id, @RequestBody ExerciseUpdateRequest request) {
        Exercise exerciseToUpdate = exerciseService.update(id, exercise -> exerciseMapper.update(exercise, request));
        return exerciseMapper.toDto(exerciseToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
