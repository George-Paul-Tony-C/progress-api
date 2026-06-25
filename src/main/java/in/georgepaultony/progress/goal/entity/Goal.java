package in.georgepaultony.progress.goal.entity;

import in.georgepaultony.progress.common.entity.BaseEntity;
import in.georgepaultony.progress.goal.enums.GoalStatus;
import in.georgepaultony.progress.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal extends BaseEntity {

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
    private GoalStatus status;

    @Column(
            name = "progress_percentage",
            nullable = false
    )
    private Integer progressPercentage;
}