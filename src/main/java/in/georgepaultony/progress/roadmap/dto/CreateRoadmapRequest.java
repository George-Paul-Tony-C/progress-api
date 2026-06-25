package in.georgepaultony.progress.roadmap.dto;

import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateRoadmapRequest(

        @NotBlank
        String title,

        String description,

        LocalDate targetDate,

        RoadmapStatus status

) {
}