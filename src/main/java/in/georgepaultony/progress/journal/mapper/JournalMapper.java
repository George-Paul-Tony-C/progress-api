package in.georgepaultony.progress.journal.mapper;

import in.georgepaultony.progress.journal.dto.CreateJournalRequest;
import in.georgepaultony.progress.journal.dto.JournalResponse;
import in.georgepaultony.progress.journal.entity.Journal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JournalMapper {

    Journal toEntity(CreateJournalRequest request);

    JournalResponse toResponse(Journal journal);
}