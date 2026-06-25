package in.georgepaultony.progress.common.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages,
        Boolean first,
        Boolean last
) {}