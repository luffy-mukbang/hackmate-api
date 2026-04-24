package yoon_hyojin.hackmate.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
