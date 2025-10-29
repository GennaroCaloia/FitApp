package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.ExerciseDto;
import it.gennaro.fitapp.dto.request.exercise.ExerciseCreateRequest;
import it.gennaro.fitapp.dto.request.exercise.ExerciseUpdateRequest;
import it.gennaro.fitapp.entity.Exercise;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Exercise toEntity(ExerciseCreateRequest request);

    ExerciseDto toDto(Exercise entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromRequest(@MappingTarget Exercise target, ExerciseUpdateRequest request);

}
