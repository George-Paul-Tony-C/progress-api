package in.georgepaultony.progress.journal.dto;

import in.georgepaultony.progress.common.dto.PaginationRequest;
import in.georgepaultony.progress.journal.enums.Mood;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JournalFilterRequest extends PaginationRequest {
    private String keyword;
    private Mood mood;
    private LocalDate fromDate;
    private LocalDate toDate;
}