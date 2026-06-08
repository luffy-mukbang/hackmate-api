package yoon_hyojin.hackmate.global.exception.user;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class UserProfileNotFoundException extends DomainException {
    public UserProfileNotFoundException() {
        super(HttpStatus.NOT_FOUND, "USER_PROFILE_NOT_FOUND", "사용자 프로필이 존재하지 않습니다.");
    }
}
