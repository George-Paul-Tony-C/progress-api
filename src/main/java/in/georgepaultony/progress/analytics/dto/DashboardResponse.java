package in.georgepaultony.progress.analytics.dto;

import in.georgepaultony.progress.journal.enums.Mood;

import java.math.BigDecimal;

public record DashboardResponse(

        Long totalJournals,

        Long totalResources,

        Long totalAttachments,

        BigDecimal totalHoursSpent,

        Mood mostCommonMood

) {
}