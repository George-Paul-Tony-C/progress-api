package in.georgepaultony.progress.journal.service;

import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.journal.dto.CreateJournalRequest;
import in.georgepaultony.progress.journal.dto.JournalFilterRequest;
import in.georgepaultony.progress.journal.dto.JournalResponse;
import in.georgepaultony.progress.journal.dto.UpdateJournalRequest;

import java.util.UUID;

public interface JournalService {

    JournalResponse create(CreateJournalRequest request);
    JournalResponse getById(UUID journalId);
    PagedResponse<JournalResponse> getAll(JournalFilterRequest request);
    JournalResponse update(UUID journalId, UpdateJournalRequest request);
    void delete(UUID journalId);
}