package com.hackmate.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_notices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(name="content", columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
