package yoon_hyojin.hackmate.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.domain.team.enums.TeamRole;
import yoon_hyojin.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="role", nullable = false)
    private TeamRole role;

    @CreatedDate
    @Column(name="joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
