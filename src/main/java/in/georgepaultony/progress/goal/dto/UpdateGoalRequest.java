package in.georgepaultony.progress.goal.dto;

import in.georgepaultony.progress.goal.enums.GoalStatus;

import java.time.LocalDate;

public record UpdateGoalRequest(

        String title,

        String description,

        LocalDate targetDate,

        GoalStatus status,

        Integer progressPercentage

) {
}