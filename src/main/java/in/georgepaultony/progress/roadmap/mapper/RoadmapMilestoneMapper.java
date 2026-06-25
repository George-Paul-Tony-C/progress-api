package in.georgepaultony.progress.roadmap.mapper;

import in.georgepaultony.progress.roadmap.dto.CreateMilestoneRequest;
import in.georgepaultony.progress.roadmap.dto.MilestoneResponse;
import in.georgepaultony.progress.roadmap.entity.RoadmapMilestone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoadmapMilestoneMapper {

    RoadmapMilestone toEntity(
            CreateMilestoneRequest request
    );

    MilestoneResponse toResponse(
            RoadmapMilestone milestone
    );
}