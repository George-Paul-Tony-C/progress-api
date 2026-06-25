package in.georgepaultony.progress.resource.entity;

import in.georgepaultony.progress.common.entity.BaseEntity;
import in.georgepaultony.progress.journal.entity.Journal;
import in.georgepaultony.progress.resource.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "journal_resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "journal_id", nullable = false)
    private Journal journal;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;
}