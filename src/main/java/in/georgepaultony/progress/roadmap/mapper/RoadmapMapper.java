package in.georgepaultony.progress.roadmap.mapper;

import in.georgepaultony.progress.roadmap.dto.CreateRoadmapRequest;
import in.georgepaultony.progress.roadmap.dto.RoadmapResponse;
import in.georgepaultony.progress.roadmap.entity.Roadmap;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoadmapMapper {

    Roadmap toEntity(
            CreateRoadmapRequest request
    );

    RoadmapResponse toResponse(
            Roadmap roadmap
    );
}