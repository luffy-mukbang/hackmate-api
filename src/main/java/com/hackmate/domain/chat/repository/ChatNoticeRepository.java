package com.hackmate.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.chat.entity.ChatNotice;

public interface ChatNoticeRepository extends JpaRepository<ChatNotice, Long> {
}
