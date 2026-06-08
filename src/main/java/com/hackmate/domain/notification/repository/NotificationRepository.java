package com.hackmate.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
