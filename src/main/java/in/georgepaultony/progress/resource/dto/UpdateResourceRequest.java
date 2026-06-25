package in.georgepaultony.progress.resource.dto;

import in.georgepaultony.progress.resource.enums.ResourceType;

public record UpdateResourceRequest(

        ResourceType resourceType,

        String title,

        String url

) {
}