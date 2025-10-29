package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.Plan;
import it.gennaro.fitapp.entity.User;
import it.gennaro.fitapp.repository.PlanRepository;
import it.gennaro.fitapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.function.Consumer;

@Service
@Profile("dev")
public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public PlanService(PlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<Plan> myPlans(String username, Pageable pageable) {
        User owner = userRepository.findByUsername(username).orElseThrow();
        return planRepository.findByOwner(owner, pageable);
    }

    @Transactional
    public Plan create(String username, Plan plan) {
        User owner = userRepository.findByUsername(username).orElseThrow();
        plan.setOwner(owner);
        plan.setCreatedAt(OffsetDateTime.now());
        return planRepository.save(plan);
    }

    @Transactional
    public Plan update(String username, Long id, Consumer<Plan> mutator) {
        Plan p = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plan not found"));
        if (!p.getOwner().getUsername().equals(username)) throw new EntityNotFoundException("Plan not found");
        mutator.accept(p);
        p.setUpdatedAt(OffsetDateTime.now());
        return p;
    }

    @Transactional
    public void delete(String username, Long id) {
        Plan p = planRepository.findById(id).orElseThrow();
        if (!p.getOwner().getUsername().equals(username)) throw new EntityNotFoundException("Plan not found");
        planRepository.delete(p);
    }

    @Transactional(readOnly = true)
    public Plan get(String username, Long id) {
        Plan p = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plan not found"));
        if (!p.getOwner().getUsername().equals(username)) throw new EntityNotFoundException("Plan not found");
        return p;
    }
}
