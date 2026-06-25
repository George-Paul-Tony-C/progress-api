package in.georgepaultony.progress.analytics.service;

import in.georgepaultony.progress.analytics.dto.DashboardResponse;
import in.georgepaultony.progress.attachment.repository.AttachmentRepository;
import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.journal.entity.Journal;
import in.georgepaultony.progress.journal.enums.Mood;
import in.georgepaultony.progress.journal.repository.JournalRepository;
import in.georgepaultony.progress.resource.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl
        implements AnalyticsService {

    private final CurrentUserProvider currentUserProvider;

    private final JournalRepository journalRepository;

    private final ResourceRepository resourceRepository;

    private final AttachmentRepository attachmentRepository;

    @Override
    public DashboardResponse getDashboard() {

        UUID userId =
                currentUserProvider.getId();

        List<Journal> journals =
                journalRepository
                        .findByUserIdAndIsDeletedFalse(
                                userId
                        );

        Long totalJournals =
                (long) journals.size();

        Long totalResources =
                resourceRepository
                        .countByJournalUserIdAndIsDeletedFalse(
                                userId
                        );

        Long totalAttachments =
                attachmentRepository
                        .countByJournalUserIdAndIsDeletedFalse(
                                userId
                        );

        BigDecimal totalHoursSpent =
                journals.stream()
                        .map(journal ->
                                journal.getHoursSpent() == null
                                        ? BigDecimal.ZERO
                                        : journal.getHoursSpent()
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        Mood mostCommonMood =
                journals.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Journal::getMood,
                                        Collectors.counting()
                                )
                        )
                        .entrySet()
                        .stream()
                        .max(
                                Comparator.comparingLong(
                                        Map.Entry::getValue
                                )
                        )
                        .map(
                                Map.Entry::getKey
                        )
                        .orElse(null);

        return new DashboardResponse(
                totalJournals,
                totalResources,
                totalAttachments,
                totalHoursSpent,
                mostCommonMood
        );
    }
}