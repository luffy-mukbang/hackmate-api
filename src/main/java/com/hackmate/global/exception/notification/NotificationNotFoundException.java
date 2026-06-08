package yoon_hyojin.hackmate.global.exception.notification;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class NotificationNotFoundException extends DomainException {
    public NotificationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND", "존재하지 않는 알림입니다.");
    }
}
