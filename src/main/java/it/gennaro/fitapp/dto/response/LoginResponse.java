package it.gennaro.fitapp.dto.response;

import java.time.Instant;

public class LoginResponse {
    private String accessToken;
    private Instant expiresAt;

    public LoginResponse(String accessToken, Instant expiresAt) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
