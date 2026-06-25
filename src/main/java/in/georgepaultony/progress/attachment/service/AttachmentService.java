package in.georgepaultony.progress.attachment.service;

import in.georgepaultony.progress.attachment.dto.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {

    AttachmentResponse upload(
            UUID journalId,
            MultipartFile file
    );

    List<AttachmentResponse> getByJournal(
            UUID journalId
    );

    void delete(
            UUID attachmentId
    );
}