package yoon_hyojin.hackmate.domain.hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "hackathon_wishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class HackathonWish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="hackathon_id", nullable = false)
    private Hackathon hackathon;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;
}
