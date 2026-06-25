package in.georgepaultony.progress.resource.dto;

import in.georgepaultony.progress.resource.enums.ResourceType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResourceResponse(

        UUID id,

        ResourceType resourceType,

        String title,

        String url,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}