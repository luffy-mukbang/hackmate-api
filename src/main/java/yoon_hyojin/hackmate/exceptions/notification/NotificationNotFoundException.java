package yoon_hyojin.hackmate.exceptions.notification;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class NotificationNotFoundException extends DomainException {
    public NotificationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND", "존재하지 않는 알림입니다.");
    }
}
