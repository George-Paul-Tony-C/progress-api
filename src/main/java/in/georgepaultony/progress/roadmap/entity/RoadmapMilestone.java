package in.georgepaultony.progress.roadmap.entity;

import in.georgepaultony.progress.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roadmap_milestones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapMilestone extends BaseEntity {

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "roadmap_id",
            nullable = false
    )
    private Roadmap roadmap;

    @Column(
            nullable = false,
            length = 255
    )
    private String title;

    @Column(
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(nullable = false)
    private Boolean completed;

    @Column(name = "order_number")
    private Integer orderNumber;
}