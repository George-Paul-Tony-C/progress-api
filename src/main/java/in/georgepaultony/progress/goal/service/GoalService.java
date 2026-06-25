package in.georgepaultony.progress.goal.service;

import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.goal.dto.CreateGoalRequest;
import in.georgepaultony.progress.goal.dto.GoalFilterRequest;
import in.georgepaultony.progress.goal.dto.GoalResponse;
import in.georgepaultony.progress.goal.dto.UpdateGoalRequest;

import java.util.UUID;

public interface GoalService {

    GoalResponse create(
            CreateGoalRequest request
    );

    GoalResponse getById(
            UUID goalId
    );

    PagedResponse<GoalResponse> getAll(
            GoalFilterRequest request
    );

    GoalResponse update(
            UUID goalId,
            UpdateGoalRequest request
    );

    void delete(
            UUID goalId
    );
}