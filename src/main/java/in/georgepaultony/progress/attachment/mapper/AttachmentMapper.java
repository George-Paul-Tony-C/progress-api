package in.georgepaultony.progress.attachment.mapper;

import in.georgepaultony.progress.attachment.dto.AttachmentResponse;
import in.georgepaultony.progress.attachment.entity.Attachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentResponse toResponse(
            Attachment attachment
    );
}