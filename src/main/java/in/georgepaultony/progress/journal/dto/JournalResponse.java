package in.georgepaultony.progress.journal.dto;

import in.georgepaultony.progress.journal.enums.Mood;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record JournalResponse(
        UUID id,
        LocalDate entryDate,
        String title,
        String summary,
        String notes,
        Mood mood,
        BigDecimal hoursSpent,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}