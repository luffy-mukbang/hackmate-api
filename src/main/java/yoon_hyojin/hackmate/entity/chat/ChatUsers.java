package yoon_hyojin.hackmate.entity.chat;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoon_hyojin.hackmate.entity.user.Users;
import yoon_hyojin.hackmate.enums.ChatRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="chat_room_id", nullable = false)
    private ChatRooms chatRooms;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="last_read_message_id")
    private ChatMessages lastReadMessage;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="deleted_at")
    private LocalDate deletedAt;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatRole role;

}
