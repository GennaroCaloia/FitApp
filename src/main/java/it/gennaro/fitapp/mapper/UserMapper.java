package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.UserDto;
import it.gennaro.fitapp.dto.UserListDto;
import it.gennaro.fitapp.entity.Role;
import it.gennaro.fitapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    UserDto toDto(User user);

    default Set<String> mapRoles(User user) {
        return user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet());
    }

    default UserListDto toListDto(User user) {
        var dto = new UserListDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.enabled = user.isEnabled();
        dto.roles = mapRoles(user);
        return dto;
    }
}