package yoon_hyojin.hackmate.global.exception.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class VerificationCodeExpiredException extends DomainException {
    public VerificationCodeExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "VERIFICATION_CODE_EXPIRED", "만료된 인증 코드입니다.");
    }
}
