package in.georgepaultony.progress.roadmap.dto;

public record UpdateMilestoneRequest(

        String title,

        String description,

        Boolean completed,

        Integer orderNumber

) {
}