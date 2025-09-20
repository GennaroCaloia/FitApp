package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.ExerciseDto;
import it.gennaro.fitapp.dto.request.ExerciseCreateRequest;
import it.gennaro.fitapp.dto.request.ExerciseUpdateRequest;
import it.gennaro.fitapp.entity.Exercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise toEntity(ExerciseCreateRequest request);

    ExerciseDto toDto(Exercise entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Exercise target, ExerciseUpdateRequest request);

}
