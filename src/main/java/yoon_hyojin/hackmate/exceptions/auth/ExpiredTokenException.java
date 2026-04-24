package yoon_hyojin.hackmate.exceptions.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class ExpiredTokenException extends DomainException {
    public ExpiredTokenException() {
        super(HttpStatus.UNAUTHORIZED, "EXPIRED_TOKEN", "만료된 토큰입니다.");
    }
}
