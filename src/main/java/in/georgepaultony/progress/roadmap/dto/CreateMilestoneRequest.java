package in.georgepaultony.progress.roadmap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMilestoneRequest(

        @NotBlank
        String title,

        String description,

        @NotNull
        Integer orderNumber

) {
}