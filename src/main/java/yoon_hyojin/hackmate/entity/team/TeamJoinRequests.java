package yoon_hyojin.hackmate.entity.team;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.entity.user.Users;
import yoon_hyojin.hackmate.enums.JoinRequestStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_join_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TeamJoinRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Teams teams;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private JoinRequestStatus status;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;


}
