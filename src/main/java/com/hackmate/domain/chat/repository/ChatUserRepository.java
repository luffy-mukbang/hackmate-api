package com.hackmate.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.chat.entity.ChatUser;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
