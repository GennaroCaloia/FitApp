package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.PlanDto;
import it.gennaro.fitapp.dto.request.plan.PlanCreateRequest;
import it.gennaro.fitapp.dto.request.plan.PlanUpdateRequest;
import it.gennaro.fitapp.entity.Plan;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    PlanDto toDto(Plan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Plan toEntity(PlanCreateRequest req);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromRequest(@MappingTarget Plan target, PlanUpdateRequest req);

}
