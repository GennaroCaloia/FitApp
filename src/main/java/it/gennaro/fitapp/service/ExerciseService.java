package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.Exercise;
import it.gennaro.fitapp.repository.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@Profile("dev")
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional(readOnly = true)
    public Page<Exercise> search(String name, String bodyPart, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return exerciseRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        if (bodyPart != null && !bodyPart.isBlank()) {
            return exerciseRepository.findByBodyPartContainingIgnoreCase(bodyPart, pageable);
        }
        return exerciseRepository.findAll(pageable);
    }

    @Transactional
    public Exercise create(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Transactional
    public Exercise update(Long id, Consumer<Exercise> mutator) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Esercizio non trovato"));
        mutator.accept(exercise);
        return exercise;
    }

    @Transactional
    public void delete(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Exercise getExercise(Long id) {
        return exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Esercizio non trovato"));
    }


}
