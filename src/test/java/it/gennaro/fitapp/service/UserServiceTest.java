package it.gennaro.fitapp.service;

import it.gennaro.fitapp.entity.User;
import it.gennaro.fitapp.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void register_encodes_password_and_assigns_user_role() {
        UserRepository users = mock(UserRepository.class);
        RoleRepository roles = mock(RoleRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);

        when(users.existsByUsername("bob")).thenReturn(false);
        when(users.existsByEmail("b@b.com")).thenReturn(false);
        when(enc.encode("secret")).thenReturn("{bcrypt}hash");
        when(roles.findByCode("USER")).thenReturn(Optional.empty());
        when(roles.save(any())).thenAnswer(i -> i.getArgument(0));

        UserService service = new UserService(users, roles, enc);
        service.register("bob","b@b.com","secret");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(users).save(captor.capture());
        User u = captor.getValue();
        assertEquals("bob", u.getUsername());
        assertEquals("{bcrypt}hash", u.getPasswordHash());
        assertTrue(u.getRoles().stream().anyMatch(r -> "USER".equals(r.getCode())));
    }

}