package yoon_hyojin.hackmate.exceptions.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class NotAuthenticatedException extends DomainException {
    public NotAuthenticatedException() {
        super(HttpStatus.UNAUTHORIZED, "NOT_AUTHENTICATED", "로그인이 필요합니다.");
    }
}
