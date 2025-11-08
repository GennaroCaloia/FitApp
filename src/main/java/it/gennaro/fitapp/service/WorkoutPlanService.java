package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.*;
import it.gennaro.fitapp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {

    private final PlanRepository planRepository;
    private final WorkoutRepository workoutRepository;
    private final UserService userService;

    public List<Workout> listWorkouts(String username, Long planId) {
        User me = userService.loadDomainUserByUsername(username);

        Plan plan = planRepository.findByIdAndOwner(planId, me)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        return workoutRepository.findByPlanId(plan.getId());
    }

    public void addWorkout(String username, Long planId, Long workoutId) {
        User me = userService.loadDomainUserByUsername(username);

        Plan plan = planRepository.findByIdAndOwner(planId, me)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));

        plan.getWorkouts().add(workout);
        planRepository.save(plan);
    }

    public void removeWorkout(String username, Long planId, Long workoutId) {
        User me = userService.loadDomainUserByUsername(username);

        Plan plan = planRepository.findByIdAndOwner(planId, me)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        plan.getWorkouts().removeIf(workout -> workout.getId().equals(workoutId));
        planRepository.save(plan);
    }

}
