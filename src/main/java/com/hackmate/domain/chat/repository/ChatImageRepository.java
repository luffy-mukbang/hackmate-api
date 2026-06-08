package yoon_hyojin.hackmate.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.chat.entity.ChatImage;

public interface ChatImageRepository extends JpaRepository<ChatImage, Long> {
}
