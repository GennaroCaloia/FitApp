package it.gennaro.fitapp.mapper;

import it.gennaro.fitapp.dto.WorkoutDto;
import it.gennaro.fitapp.dto.WorkoutItemDto;
import it.gennaro.fitapp.entity.Workout;
import it.gennaro.fitapp.entity.WorkoutItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    @Mapping(target = "planId", source = "plan.id")
    @Mapping(target = "items", expression = "java(toItemDtos(entity.getItems()))")
    WorkoutDto toDto(Workout entity);

    default WorkoutItemDto toItemDto(WorkoutItem wi) {
        var d = new WorkoutItemDto();
        d.id = wi.getId();
        d.exerciseId = wi.getExercise().getId();
        d.sets = wi.getSets();
        d.reps = wi.getReps();
        d.weight = wi.getWeight();
        d.restSeconds = wi.getRestSeconds();
        return d;
    }

    default java.util.List<WorkoutItemDto> toItemDtos(List<WorkoutItem> list) {
        return list == null ? java.util.List.of() : list.stream().map(this::toItemDto).toList();
    }
}
