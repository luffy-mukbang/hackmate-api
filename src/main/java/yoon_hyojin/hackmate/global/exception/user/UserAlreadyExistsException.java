package yoon_hyojin.hackmate.global.exception.user;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS", "이미 존재하는 이메일입니다.");
    }
}
