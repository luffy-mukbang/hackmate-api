package yoon_hyojin.hackmate.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.domain.team.enums.JoinRequestStatus;
import yoon_hyojin.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_join_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TeamJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private JoinRequestStatus status;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;
}
