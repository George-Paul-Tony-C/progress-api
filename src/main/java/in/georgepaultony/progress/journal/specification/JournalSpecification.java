package in.georgepaultony.progress.journal.specification;

import in.georgepaultony.progress.journal.entity.Journal;
import in.georgepaultony.progress.journal.enums.Mood;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public final class JournalSpecification {

    private JournalSpecification() {
    }

    public static Specification<Journal> belongsToUser(UUID userId) {

        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Journal> notDeleted() {

        return (root, query, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public static Specification<Journal> hasMood(Mood mood) {

        if (mood == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.equal(root.get("mood"), mood);
    }

    public static Specification<Journal> containsKeyword(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return null;
        }

        String search = "%" + keyword.toLowerCase() + "%";

        return (root, query, cb) ->
                cb.or(
                        cb.like(
                                cb.lower(root.get("title")),
                                search
                        ),
                        cb.like(
                                cb.lower(root.get("summary")),
                                search
                        ),
                        cb.like(
                                cb.lower(root.get("notes")),
                                search
                        )
                );
    }

    public static Specification<Journal> fromDate(LocalDate fromDate) {

        if (fromDate == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("entryDate"),
                        fromDate
                );
    }

    public static Specification<Journal> toDate(LocalDate toDate) {

        if (toDate == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.lessThanOrEqualTo(
                        root.get("entryDate"),
                        toDate
                );
    }
}