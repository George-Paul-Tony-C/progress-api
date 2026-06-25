package in.georgepaultony.progress.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaginationRequest {
    private Integer page = 0;
    private Integer size = 10;
}