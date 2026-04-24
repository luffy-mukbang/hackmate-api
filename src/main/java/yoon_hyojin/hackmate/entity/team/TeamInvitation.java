package yoon_hyojin.hackmate.entity.team;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.entity.user.User;
import yoon_hyojin.hackmate.enums.InvitationStatus;
import yoon_hyojin.hackmate.enums.TeamRole;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_invitations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TeamInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name="invited_user_id", nullable = false)
    private User invitedUser;

    @ManyToOne
    @JoinColumn(name="invited_by", nullable = false)
    private User invitedBy;

    @Column(name="position")
    @Enumerated(EnumType.STRING)
    private TeamRole position;

    @Column(name="message", columnDefinition = "TEXT")
    private String message;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;


}
