package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.dto.PlanDto;
import it.gennaro.fitapp.dto.request.plan.PlanCreateRequest;
import it.gennaro.fitapp.dto.request.plan.PlanUpdateRequest;
import it.gennaro.fitapp.entity.Plan;
import it.gennaro.fitapp.mapper.PlanMapper;
import it.gennaro.fitapp.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Profile("dev")
@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;
    private final PlanMapper planMapper;

    public PlanController(PlanService planService, PlanMapper planMapper) {
        this.planService = planService;
        this.planMapper = planMapper;
    }

    @GetMapping
    public Page<PlanDto> myPlans(Authentication auth,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        return planService.myPlans(auth.getName(), PageRequest.of(page, Math.min(size,100), Sort.by("id").descending()))
                .map(planMapper::toDto);
    }

    @GetMapping("/{id}")
    public PlanDto get(Authentication auth, @PathVariable Long id) {
        return planMapper.toDto(planService.get(auth.getName(), id));
    }

    @PostMapping
    public ResponseEntity<PlanDto> create(Authentication auth, @Valid @RequestBody PlanCreateRequest req) {
        Plan saved = planService.create(auth.getName(), planMapper.toEntity(req));
        return ResponseEntity.ok(planMapper.toDto(saved));
    }

    @PatchMapping("/{id}")
    public PlanDto update(Authentication auth, @PathVariable Long id, @RequestBody PlanUpdateRequest req) {
        Plan updated = planService.update(auth.getName(), id, p -> planMapper.updateEntityFromRequest(p, req));
        return planMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication auth, @PathVariable Long id) {
        planService.delete(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
