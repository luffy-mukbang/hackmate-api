package yoon_hyojin.hackmate.entity.notification;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.entity.user.Users;
import yoon_hyojin.hackmate.enums.NotificationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="receiver_id", nullable = false)
    private Users receiver;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private Users sender;

    @Column(name="type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name="target_id")
    private Long targetId;

    @Column(name="is_read", nullable = false)
    private boolean isRead;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;
}
