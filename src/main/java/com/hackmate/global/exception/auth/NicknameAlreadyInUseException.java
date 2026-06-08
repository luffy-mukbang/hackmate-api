package yoon_hyojin.hackmate.global.exception.auth;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class NicknameAlreadyInUseException extends DomainException {
    public NicknameAlreadyInUseException() {
        super(HttpStatus.CONFLICT, "NICKNAME_ALREADY_IN_USE", "이미 사용 중인 닉네임입니다.");
    }
}
