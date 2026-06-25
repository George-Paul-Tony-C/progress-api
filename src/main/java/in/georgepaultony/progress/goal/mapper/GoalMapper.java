package in.georgepaultony.progress.goal.mapper;

import in.georgepaultony.progress.goal.dto.CreateGoalRequest;
import in.georgepaultony.progress.goal.dto.GoalResponse;
import in.georgepaultony.progress.goal.entity.Goal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    Goal toEntity(CreateGoalRequest request);

    GoalResponse toResponse(Goal goal);
}