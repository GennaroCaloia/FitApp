package it.gennaro.fitapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.gennaro.fitapp.dto.RoleDto;
import it.gennaro.fitapp.dto.request.role.RoleCreateRequest;
import it.gennaro.fitapp.dto.request.role.RoleUpdateRequest;
import it.gennaro.fitapp.mapper.RoleMapper;
import it.gennaro.fitapp.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Profile("dev")
@RestController
@RequestMapping("/api/admin/roles")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public AdminRoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping
    @Operation(summary = "Lista ruoli", description = "Ricerca paginata che fa uso di sort. Es. sort=code,asc")
    public Page<RoleDto> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size,
                              @RequestParam(defaultValue = "code,asc") String sort) {
        var pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(Sort.Order.by(sort.split(",")[0])
                .with(sort.endsWith(",desc") ? Sort.Direction.DESC : Sort.Direction.ASC)));
        return roleService.list(pageable).map(roleMapper::toDto);
    }

    @GetMapping("/{id}")
    public RoleDto get(@PathVariable Long id) {
        return roleMapper.toDto(roleService.get(id));
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@Valid @RequestBody RoleCreateRequest request) {
        return ResponseEntity.ok(roleMapper.toDto(roleService.create(roleMapper.toEntity(request))));
    }

    @PatchMapping("/{id}")
    public RoleDto update(@PathVariable Long id, @RequestBody RoleUpdateRequest request) {
        var updated = roleService.update(id, ruolo -> roleMapper.updateEntityFromRequest(ruolo, request));
        return roleMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
