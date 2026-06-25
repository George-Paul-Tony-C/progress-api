package in.georgepaultony.progress.journal.repository;

import in.georgepaultony.progress.journal.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface JournalRepository
        extends JpaRepository<Journal, UUID>,
        JpaSpecificationExecutor<Journal> {

    List<Journal> findByUserIdAndIsDeletedFalse(
            UUID userId
    );
}