package in.georgepaultony.progress.roadmap.entity;

import in.georgepaultony.progress.common.entity.BaseEntity;
import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;
import in.georgepaultony.progress.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "roadmaps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roadmap extends BaseEntity {

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            nullable = false,
            length = 255
    )
    private String title;

    @Column(
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private RoadmapStatus status;
}