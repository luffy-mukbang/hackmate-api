package yoon_hyojin.hackmate.global.exception.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class VerificationCodeNotMatchException extends DomainException {
    public VerificationCodeNotMatchException() {
        super(HttpStatus.UNAUTHORIZED, "VERIFICATION_CODE_NOT_MATCH", "인증 코드가 일치하지 않습니다.");
    }
}
