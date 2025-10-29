package it.gennaro.fitapp.security;

import it.gennaro.fitapp.config.JwtProperties;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @Test
    void generate_and_validate_token() {
        JwtProperties props = new JwtProperties();
        props.setIssuer("fitapp-test");
        // "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" come bytes => base64 = "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWE="
        props.setSecret("YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWE=");
        props.setTtlMinutes(60);

        JwtService jwt = new JwtService(props);

        var principal = User.withUsername("alice")
                .password("pw")
                .roles("USER")
                .build();

        String token = jwt.generateToken(principal);

        assertNotNull(token, "token should not be null");
        assertTrue(jwt.isValid(token), "token should be valid");
        assertEquals("alice", jwt.extractUsername(token), "username should match");
    }

}