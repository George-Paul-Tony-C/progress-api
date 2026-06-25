package in.georgepaultony.progress.attachment.controller;

import in.georgepaultony.progress.attachment.dto.AttachmentResponse;
import in.georgepaultony.progress.attachment.service.AttachmentService;
import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(
            value = "/api/v1/journals/{journalId}/attachments",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ApiResponse<AttachmentResponse>> upload(
            @PathVariable UUID journalId,
            @RequestPart("file") MultipartFile file
    ) {

        return ResponseUtil.created(
                "Attachment uploaded successfully",
                attachmentService.upload(
                        journalId,
                        file
                )
        );
    }

    @GetMapping("/api/v1/journals/{journalId}/attachments")
    public ResponseEntity<ApiResponse<List<AttachmentResponse>>> getByJournal(
            @PathVariable UUID journalId
    ) {

        return ResponseUtil.ok(
                "Attachments fetched successfully",
                attachmentService.getByJournal(journalId)
        );
    }

    @DeleteMapping("/api/v1/attachments/{attachmentId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID attachmentId
    ) {

        attachmentService.delete(attachmentId);

        return ResponseUtil.ok(
                "Attachment deleted successfully",
                null
        );
    }
}