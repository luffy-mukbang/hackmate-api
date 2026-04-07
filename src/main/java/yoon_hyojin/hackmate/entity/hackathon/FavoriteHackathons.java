package yoon_hyojin.hackmate.entity.hackathon;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.entity.user.Users;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_hackathons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FavoriteHackathons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name="hackathon_id", nullable = false)
    private Hackathons hackathons;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

}
