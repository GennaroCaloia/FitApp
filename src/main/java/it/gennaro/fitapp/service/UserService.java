package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.Role;
import it.gennaro.fitapp.entity.User;
import it.gennaro.fitapp.repository.RoleRepository;
import it.gennaro.fitapp.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Profile("dev")
public class UserService {

    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, RoleRepository roles, PasswordEncoder encoder) {
        this.users = users;
        this.roles = roles;
        this.encoder = encoder;
    }

    @Transactional
    public User register(String username, String email, String password) {
        if (users.existsByUsername(username)) {
            throw new IllegalArgumentException("Username già utilizzato");
        }
        if (users.existsByEmail(email)) {
            throw new IllegalArgumentException("Email già utilizzata");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setEnabled(true);
        user.setCreatedAt(OffsetDateTime.now());

        Role ruoloUtente = roles.findByCode("USER").orElseGet(() -> {
            Role ruolo = new Role();
            ruolo.setCode("USER");
            ruolo.setDescription("Utente Standard");

            return roles.save(ruolo);
        });

        user.getRoles().add(ruoloUtente);

        return users.save(user);
    }

}
