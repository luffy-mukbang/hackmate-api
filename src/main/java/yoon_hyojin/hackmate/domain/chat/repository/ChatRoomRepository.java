package yoon_hyojin.hackmate.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
