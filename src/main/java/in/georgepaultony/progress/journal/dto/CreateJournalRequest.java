package in.georgepaultony.progress.journal.dto;

import in.georgepaultony.progress.journal.enums.Mood;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateJournalRequest(

        @NotNull
        LocalDate entryDate,

        @NotBlank
        String title,

        String summary,

        String notes,

        @NotNull
        Mood mood,

        BigDecimal hoursSpent

) {}