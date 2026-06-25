package in.georgepaultony.progress.journal.entity;

import in.georgepaultony.progress.common.entity.BaseEntity;
import in.georgepaultony.progress.journal.enums.Mood;
import in.georgepaultony.progress.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "journals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(name = "entry_date" , nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false , length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Mood mood;

    @Column(name = "hours_spent" , precision = 5, scale = 2)
    private BigDecimal hoursSpent;
}
