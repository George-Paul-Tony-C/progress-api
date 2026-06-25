package in.georgepaultony.progress.roadmap.service;

import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.roadmap.dto.CreateRoadmapRequest;
import in.georgepaultony.progress.roadmap.dto.RoadmapFilterRequest;
import in.georgepaultony.progress.roadmap.dto.RoadmapResponse;
import in.georgepaultony.progress.roadmap.dto.UpdateRoadmapRequest;

import java.util.UUID;

public interface RoadmapService {

    RoadmapResponse create(
            CreateRoadmapRequest request
    );

    RoadmapResponse getById(
            UUID roadmapId
    );

    PagedResponse<RoadmapResponse> getAll(
            RoadmapFilterRequest request
    );

    RoadmapResponse update(
            UUID roadmapId,
            UpdateRoadmapRequest request
    );

    void delete(
            UUID roadmapId
    );
}