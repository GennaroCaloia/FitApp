package it.gennaro.fitapp.dto;

import java.util.Set;

public class UserListDto {
    public Long id;
    public String username;
    public String email;
    public boolean enabled;
    public Set<String> roles;
}
