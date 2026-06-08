package com.hackmate.global.exception.user;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 사용자입니다.");
    }
}
