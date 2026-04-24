package yoon_hyojin.hackmate.exceptions.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class InvalidTokenException extends DomainException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 토큰입니다.");
    }
}
