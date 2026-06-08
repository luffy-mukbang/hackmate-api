package yoon_hyojin.hackmate.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.domain.team.enums.ActivityType;
import yoon_hyojin.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="leader_id", nullable = false)
    private User leader;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="activity_type")
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Column(name="schedule")
    private String schedule;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
