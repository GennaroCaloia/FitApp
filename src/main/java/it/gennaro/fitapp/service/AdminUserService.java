package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.Role;
import it.gennaro.fitapp.entity.User;
import it.gennaro.fitapp.repository.RoleRepository;
import it.gennaro.fitapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.function.Consumer;

@Service
public class AdminUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
    }

    @Transactional
    public User update(Long id, Consumer<User> mutator) {
        var user = get(id);
        mutator.accept(user);
        return user;
    }

    @Transactional
    public void setRoles(Long id, Set<String> roleCodes) {
        var user = get(id);
        user.getRoles().clear();
        for (String code : roleCodes) {
            Role ruolo = roleRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Ruolo non trovato: " + code));
            user.getRoles().add(ruolo);
        }
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
