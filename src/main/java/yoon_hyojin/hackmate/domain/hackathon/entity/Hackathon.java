package yoon_hyojin.hackmate.domain.hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.domain.hackathon.enums.HackathonStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "hackathons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="organizer", nullable = false)
    private String organizer;

    @Column(name="hackathonStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private HackathonStatus status;

    @Column(name="thumbnail_url")
    private String thumbnailUrl;

    @Column(name="wish_count", nullable = false)
    private int wishCount;

    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name="location")
    private String location;

    @Column(name="prize_info")
    private String prizeInfo;

    @Column(name="target")
    private String target;

    @Column(name="homepage_url")
    private String homepageUrl;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
