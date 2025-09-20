package it.gennaro.fitapp.config;

import it.gennaro.fitapp.entity.User;
import it.gennaro.fitapp.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

@Configuration
@Profile("dev")
public class JpaUsersConfig {

    @Bean
    public UserDetailsService userDetailsService(@Lazy UserRepository users) {
        return username -> {
            User u = users.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            var authorities = u.getRoles().stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getCode()))
                    .collect(Collectors.toList());
            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password(u.getPasswordHash())
                    .authorities(authorities)
                    .disabled(!u.isEnabled())
                    .build();
        };
    }

}
