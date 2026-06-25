package in.georgepaultony.progress.goal.dto;

import in.georgepaultony.progress.common.dto.PaginationRequest;
import in.georgepaultony.progress.goal.enums.GoalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalFilterRequest
        extends PaginationRequest {

    private GoalStatus status;

    private String keyword;
}