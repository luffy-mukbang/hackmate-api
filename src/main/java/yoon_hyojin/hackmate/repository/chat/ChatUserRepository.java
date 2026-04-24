package yoon_hyojin.hackmate.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.chat.ChatUser;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
