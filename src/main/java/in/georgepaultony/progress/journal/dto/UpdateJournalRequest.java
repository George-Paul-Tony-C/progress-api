package in.georgepaultony.progress.journal.dto;

import in.georgepaultony.progress.journal.enums.Mood;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateJournalRequest(

        LocalDate entryDate,

        String title,

        String summary,

        String notes,

        Mood mood,

        BigDecimal hoursSpent

) {}