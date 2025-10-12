package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.Role;
import it.gennaro.fitapp.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<Role> list(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Transactional
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public Role update(Long id, Consumer<Role> mutator) {
        var r = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ruolo non trovato"));
        mutator.accept(r);
        return r;
    }

    @Transactional
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Role get(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ruolo non trovato"));
    }
}
