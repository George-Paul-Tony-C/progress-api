package in.georgepaultony.progress.goal.dto;

import in.georgepaultony.progress.goal.enums.GoalStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateGoalRequest(

        @NotBlank
        String title,

        String description,

        LocalDate targetDate,

        GoalStatus status,

        @Min(0)
        @Max(100)
        Integer progressPercentage

) {
}