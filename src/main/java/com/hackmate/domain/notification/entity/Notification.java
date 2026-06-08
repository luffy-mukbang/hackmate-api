package com.hackmate.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.hackmate.domain.notification.enums.NotificationType;
import com.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="receiver_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private User sender;

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
