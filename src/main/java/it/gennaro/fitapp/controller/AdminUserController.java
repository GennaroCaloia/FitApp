package it.gennaro.fitapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.gennaro.fitapp.dto.UserListDto;
import it.gennaro.fitapp.dto.request.user.UserUpdateRequest;
import it.gennaro.fitapp.mapper.UserMapper;
import it.gennaro.fitapp.service.AdminUserService;
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
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
    private final AdminUserService adminUserService;
    private final UserMapper userMapper;

    public AdminUserController(AdminUserService adminUserService, UserMapper userMapper) {
        this.adminUserService = adminUserService;
        this.userMapper = userMapper;
    }

    @GetMapping
    @Operation(summary = "Lista utenti", description = "Ricerca paginata; sort es. sort=username,asc")
    public Page<UserListDto> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "id,desc") String sort) {
        var parts = sort.split(",");
        var pageable = PageRequest.of(page, Math.min(size, 100),
                Sort.by(new Sort.Order(parts.length > 0 ? Sort.Direction.fromOptionalString(parts.length > 1 ? parts[1] : "desc").orElse(Sort.Direction.DESC) : Sort.Direction.DESC,
                        parts[0])));
        return adminUserService.list(pageable).map(userMapper::toListDto);
    }

    @GetMapping("/{id}")
    public UserListDto get(@PathVariable Long id) {
        return userMapper.toListDto(adminUserService.get(id));
    }

    @PatchMapping("/{id}")
    public UserListDto update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        var updated = adminUserService.update(id, u -> {
            if (request.username != null) u.setUsername(request.username);
            if (request.email != null) u.setEmail(request.email);
            if (request.enabled != null) u.setEnabled(request.enabled);
        });
        if (request.roles != null) adminUserService.setRoles(id, request.roles);
        return userMapper.toListDto(adminUserService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
