package in.georgepaultony.progress.roadmap.specification;

import in.georgepaultony.progress.roadmap.entity.Roadmap;
import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class RoadmapSpecification {

    private RoadmapSpecification() {
    }

    public static Specification<Roadmap> belongsToUser(
            UUID userId
    ) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("user").get("id"),
                        userId
                );
    }

    public static Specification<Roadmap> notDeleted() {

        return (root, query, cb) ->
                cb.isFalse(
                        root.get("isDeleted")
                );
    }

    public static Specification<Roadmap> hasStatus(
            RoadmapStatus status
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

    public static Specification<Roadmap> containsKeyword(
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