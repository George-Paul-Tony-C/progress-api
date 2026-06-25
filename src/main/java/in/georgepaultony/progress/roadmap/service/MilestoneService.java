package in.georgepaultony.progress.roadmap.service;

import in.georgepaultony.progress.roadmap.dto.CreateMilestoneRequest;
import in.georgepaultony.progress.roadmap.dto.MilestoneResponse;
import in.georgepaultony.progress.roadmap.dto.UpdateMilestoneRequest;

import java.util.List;
import java.util.UUID;

public interface MilestoneService {

    MilestoneResponse create(
            UUID roadmapId,
            CreateMilestoneRequest request
    );

    List<MilestoneResponse> getByRoadmap(
            UUID roadmapId
    );

    MilestoneResponse update(
            UUID milestoneId,
            UpdateMilestoneRequest request
    );

    MilestoneResponse moveUp(
            UUID milestoneId
    );

    MilestoneResponse moveDown(
            UUID milestoneId
    );

    void delete(
            UUID milestoneId
    );
}