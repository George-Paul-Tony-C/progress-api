package in.georgepaultony.progress.roadmap.dto;

import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;

import java.time.LocalDate;

public record UpdateRoadmapRequest(

        String title,

        String description,

        LocalDate targetDate,

        RoadmapStatus status

) {
}