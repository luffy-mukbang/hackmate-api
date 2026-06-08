package com.hackmate.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
