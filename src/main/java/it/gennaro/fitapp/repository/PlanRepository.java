package it.gennaro.fitapp.repository;

import it.gennaro.fitapp.entity.Plan;
import it.gennaro.fitapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Page<Plan> findByOwner(User owner, Pageable pageable);

    Optional<Plan> findByIdAndOwner(Long id, User owner);
}
