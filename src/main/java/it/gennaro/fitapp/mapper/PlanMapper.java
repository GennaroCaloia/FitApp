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

    Plan toEntity(PlanCreateRequest req);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Plan target, PlanUpdateRequest req);

}
