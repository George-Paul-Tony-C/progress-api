package in.georgepaultony.progress.roadmap.dto;

import in.georgepaultony.progress.common.dto.PaginationRequest;
import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoadmapFilterRequest extends PaginationRequest {

    private RoadmapStatus status;

    private String keyword;
}