package in.georgepaultony.progress.goal.dto;

import in.georgepaultony.progress.goal.enums.GoalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GoalResponse(

        UUID id,

        String title,

        String description,

        LocalDate targetDate,

        GoalStatus status,

        Integer progressPercentage,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}