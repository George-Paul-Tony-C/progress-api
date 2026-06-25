package in.georgepaultony.progress.goal.repository;

import in.georgepaultony.progress.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface GoalRepository
        extends JpaRepository<Goal, UUID>,
        JpaSpecificationExecutor<Goal> {
}