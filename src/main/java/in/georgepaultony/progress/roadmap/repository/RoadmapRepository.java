package in.georgepaultony.progress.roadmap.repository;

import in.georgepaultony.progress.roadmap.entity.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RoadmapRepository
        extends JpaRepository<Roadmap, UUID>,
        JpaSpecificationExecutor<Roadmap> {
}