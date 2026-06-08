package com.hackmate.global.exception.auth;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class EmailAlreadyInUseException extends DomainException {
    public EmailAlreadyInUseException() {
        super(HttpStatus.CONFLICT, "EMAIL_ALREADY_IN_USE", "이미 사용 중인 이메일입니다.");
    }
}
