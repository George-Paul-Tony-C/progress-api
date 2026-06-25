package in.georgepaultony.progress.roadmap.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MilestoneResponse(

        UUID id,

        String title,

        String description,

        Boolean completed,

        Integer orderNumber,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}