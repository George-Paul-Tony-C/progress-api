package in.georgepaultony.progress.resource.dto;

import in.georgepaultony.progress.resource.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateResourceRequest(

        @NotNull
        ResourceType resourceType,

        @NotBlank
        String title,

        @NotBlank
        String url

) {
}