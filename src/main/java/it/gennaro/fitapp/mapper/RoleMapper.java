package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.RoleDto;
import it.gennaro.fitapp.dto.request.role.RoleCreateRequest;
import it.gennaro.fitapp.dto.request.role.RoleUpdateRequest;
import it.gennaro.fitapp.entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role entity);

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(@MappingTarget Role entity, RoleUpdateRequest request);
}
