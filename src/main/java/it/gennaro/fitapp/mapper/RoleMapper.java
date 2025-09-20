package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.RoleDto;
import it.gennaro.fitapp.dto.request.role.RoleCreateRequest;
import it.gennaro.fitapp.dto.request.role.RoleUpdateRequest;
import it.gennaro.fitapp.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role entity);
    Role toEntity(RoleCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Role entity, RoleUpdateRequest request);
}
