package yoon_hyojin.hackmate.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.chat.ChatImage;

public interface ChatImageRepository extends JpaRepository<ChatImage, Long> {
}
