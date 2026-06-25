package in.georgepaultony.progress.attachment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AttachmentResponse(

        UUID id,

        String fileName,

        String fileUrl,

        String contentType,

        Long fileSize,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}