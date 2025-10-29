package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.Workout;
import it.gennaro.fitapp.entity.WorkoutItem;
import it.gennaro.fitapp.repository.ExerciseRepository;
import it.gennaro.fitapp.repository.PlanRepository;
import it.gennaro.fitapp.repository.UserRepository;
import it.gennaro.fitapp.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Consumer;

@Service
@Profile("dev")
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final PlanRepository planRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository, PlanRepository planRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.planRepository = planRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<Workout> latest(String username, int page, int size) {
        var owner = userRepository.findByUsername(username).orElseThrow();
        return workoutRepository.findLatestByOwner(owner, PageRequest.of(page, Math.min(size,100)));
    }

    @Transactional
    public Workout create(String username, Long planId, Consumer<Workout> mutator) {
        var p = planRepository.findById(planId).orElseThrow(() -> new EntityNotFoundException("Plan not found"));
        if (!p.getOwner().getUsername().equals(username)) throw new EntityNotFoundException("Plan not found");

        var w = new Workout();
        w.setPlan(p);
        w.setStartedAt(OffsetDateTime.now());
        mutator.accept(w);
        return workoutRepository.save(w);
    }

    @Transactional(readOnly = true)
    public Workout detail(String username, Long id) {
        var owner = userRepository.findByUsername(username).orElseThrow();
        var list = workoutRepository.findDetailByIdAndOwner(id, owner);
        if (list.isEmpty()) throw new EntityNotFoundException("Workout not found");
        return list.get(0);
    }

    @Transactional
    public void addItem(Workout w, Long exerciseId, Integer sets, Integer reps, BigDecimal weight, Integer rest) {
        var e = exerciseRepository.findById(exerciseId).orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
        var wi = new WorkoutItem();
        wi.setWorkout(w);
        wi.setExercise(e);
        wi.setSets(sets);
        wi.setReps(reps);
        wi.setWeight(weight);
        wi.setRestSeconds(rest);
        w.getItems().add(wi);
    }
}
