package in.georgepaultony.progress.roadmap.dto;

import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RoadmapResponse(

        UUID id,

        String title,

        String description,

        LocalDate targetDate,

        RoadmapStatus status,

        Integer totalMilestones,

        Integer completedMilestones,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}