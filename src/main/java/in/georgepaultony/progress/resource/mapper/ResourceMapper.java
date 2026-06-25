package in.georgepaultony.progress.resource.mapper;

import in.georgepaultony.progress.resource.dto.CreateResourceRequest;
import in.georgepaultony.progress.resource.dto.ResourceResponse;
import in.georgepaultony.progress.resource.entity.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    Resource toEntity(CreateResourceRequest request);

    ResourceResponse toResponse(Resource resource);
}