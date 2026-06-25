package in.georgepaultony.progress.attachment.service;

import in.georgepaultony.progress.attachment.dto.AttachmentResponse;
import in.georgepaultony.progress.attachment.entity.Attachment;
import in.georgepaultony.progress.attachment.mapper.AttachmentMapper;
import in.georgepaultony.progress.attachment.repository.AttachmentRepository;
import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.exception.ForbiddenException;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.journal.entity.Journal;
import in.georgepaultony.progress.journal.repository.JournalRepository;
import in.georgepaultony.progress.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final JournalRepository journalRepository;
    private final AttachmentMapper attachmentMapper;
    private final CurrentUserProvider currentUserProvider;
    private final StorageService storageService;

    @Override
    public AttachmentResponse upload(
            UUID journalId,
            MultipartFile file
    ) {

        UUID userId = currentUserProvider.getId();

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Journal not found"));

        if (!journal.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }

        String folder =
                "journal-attachments/" + userId;

        String fileUrl =
                storageService.uploadFile(
                        file,
                        folder
                );

        Attachment attachment = Attachment.builder()
                .journal(journal)
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .storagePath(folder)
                .build();

        attachment = attachmentRepository.save(attachment);

        return attachmentMapper.toResponse(attachment);
    }

    @Override
    public List<AttachmentResponse> getByJournal(
            UUID journalId
    ) {

        UUID userId = currentUserProvider.getId();

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Journal not found"));

        if (!journal.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }

        return attachmentRepository
                .findByJournalIdAndIsDeletedFalse(journalId)
                .stream()
                .map(attachmentMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID attachmentId) {

        UUID userId = currentUserProvider.getId();

        Attachment attachment =
                attachmentRepository.findById(attachmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Attachment not found"));

        if (!attachment.getJournal()
                .getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException("Access denied");
        }

        attachment.setIsDeleted(true);

        attachmentRepository.save(attachment);
    }
}