package yoon_hyojin.hackmate.exceptions.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class InvalidPasswordException extends DomainException {
    public InvalidPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "비밀번호가 올바르지 않습니다.");
    }
}
