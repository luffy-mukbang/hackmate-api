package com.hackmate.global.exception.auth;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class NotAuthenticatedException extends DomainException {
    public NotAuthenticatedException() {
        super(HttpStatus.UNAUTHORIZED, "NOT_AUTHENTICATED", "로그인이 필요합니다.");
    }
}
