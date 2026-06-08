package com.hackmate.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
