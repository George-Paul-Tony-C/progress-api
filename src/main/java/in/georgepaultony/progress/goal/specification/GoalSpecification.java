package in.georgepaultony.progress.goal.specification;

import in.georgepaultony.progress.goal.entity.Goal;
import in.georgepaultony.progress.goal.enums.GoalStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class GoalSpecification {

    private GoalSpecification() {
    }

    public static Specification<Goal> belongsToUser(
            UUID userId
    ) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("user").get("id"),
                        userId
                );
    }

    public static Specification<Goal> notDeleted() {

        return (root, query, cb) ->
                cb.isFalse(
                        root.get("isDeleted")
                );
    }

    public static Specification<Goal> hasStatus(
            GoalStatus status
    ) {

        if (status == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.equal(
                        root.get("status"),
                        status
                );
    }

    public static Specification<Goal> containsKeyword(
            String keyword
    ) {

        if (keyword == null ||
                keyword.isBlank()) {

            return null;
        }

        String search =
                "%" +
                        keyword.toLowerCase()
                        + "%";

        return (root, query, cb) ->
                cb.or(
                        cb.like(
                                cb.lower(
                                        root.get("title")
                                ),
                                search
                        ),
                        cb.like(
                                cb.lower(
                                        root.get("description")
                                ),
                                search
                        )
                );
    }
}