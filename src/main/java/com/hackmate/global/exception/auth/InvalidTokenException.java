package com.hackmate.global.exception.auth;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class InvalidTokenException extends DomainException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 토큰입니다.");
    }
}
