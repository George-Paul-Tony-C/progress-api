package in.georgepaultony.progress.roadmap.repository;

import in.georgepaultony.progress.roadmap.entity.RoadmapMilestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoadmapMilestoneRepository
        extends JpaRepository<RoadmapMilestone, UUID> {

    List<RoadmapMilestone>
    findByRoadmapIdAndIsDeletedFalseOrderByOrderNumberAsc(
            UUID roadmapId
    );

    Integer countByRoadmapIdAndIsDeletedFalse(
            UUID roadmapId
    );

    Integer countByRoadmapIdAndCompletedTrueAndIsDeletedFalse(
            UUID roadmapId
    );
}