package in.georgepaultony.progress.journal.service;

import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.journal.dto.CreateJournalRequest;
import in.georgepaultony.progress.journal.dto.JournalFilterRequest;
import in.georgepaultony.progress.journal.dto.JournalResponse;
import in.georgepaultony.progress.journal.dto.UpdateJournalRequest;
import in.georgepaultony.progress.journal.entity.Journal;
import in.georgepaultony.progress.journal.mapper.JournalMapper;
import in.georgepaultony.progress.journal.repository.JournalRepository;
import in.georgepaultony.progress.journal.specification.JournalSpecification;
import in.georgepaultony.progress.user.entity.User;
import in.georgepaultony.progress.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;
    private final JournalMapper journalMapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public JournalResponse create(CreateJournalRequest request) {

        String email = currentUserProvider.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Journal journal = journalMapper.toEntity(request);

        journal.setUser(user);

        journal = journalRepository.save(journal);

        return journalMapper.toResponse(journal);
    }

    @Override
    public JournalResponse getById(UUID journalId) {

        UUID userId = currentUserProvider.getId();

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new RuntimeException("Journal not found"));

        if (!journal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        if (Boolean.TRUE.equals(journal.getIsDeleted())) {
            throw new RuntimeException("Journal not found");
        }

        return journalMapper.toResponse(journal);
    }

    @Override
    public PagedResponse<JournalResponse> getAll(
            JournalFilterRequest request) {

        UUID userId = currentUserProvider.getId();

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize()
        );

        Specification<Journal> specification =
                JournalSpecification.belongsToUser(userId)
                        .and(JournalSpecification.notDeleted())
                        .and(JournalSpecification.hasMood(request.getMood()))
                        .and(JournalSpecification.containsKeyword(request.getKeyword()))
                        .and(JournalSpecification.fromDate(request.getFromDate()))
                        .and(JournalSpecification.toDate(request.getToDate()));

        Page<Journal> journals =
                journalRepository.findAll(
                        specification,
                        pageable
                );

        return new PagedResponse<>(
                journals.getContent()
                        .stream()
                        .map(journalMapper::toResponse)
                        .toList(),

                journals.getNumber(),
                journals.getSize(),
                journals.getTotalElements(),
                journals.getTotalPages(),
                journals.isFirst(),
                journals.isLast()
        );
    }

    @Override
    public JournalResponse update(
            UUID journalId,
            UpdateJournalRequest request) {

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new RuntimeException("Journal not found"));

        if (request.entryDate() != null) {
            journal.setEntryDate(request.entryDate());
        }

        if (request.title() != null) {
            journal.setTitle(request.title());
        }

        if (request.summary() != null) {
            journal.setSummary(request.summary());
        }

        if (request.notes() != null) {
            journal.setNotes(request.notes());
        }

        if (request.mood() != null) {
            journal.setMood(request.mood());
        }

        if (request.hoursSpent() != null) {
            journal.setHoursSpent(request.hoursSpent());
        }

        journal = journalRepository.save(journal);

        return journalMapper.toResponse(journal);
    }

    @Override
    public void delete(UUID journalId) {

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new RuntimeException("Journal not found"));

        journal.setIsDeleted(true);

        journalRepository.save(journal);
    }
}