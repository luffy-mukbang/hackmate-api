package yoon_hyojin.hackmate.global.exception.user;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class UserDeletedException extends DomainException {
    public UserDeletedException() {
        super(HttpStatus.GONE, "USER_DELETED", "탈퇴한 사용자입니다.");
    }
}
