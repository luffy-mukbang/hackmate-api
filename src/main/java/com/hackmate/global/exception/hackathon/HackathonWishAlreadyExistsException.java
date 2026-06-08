package com.hackmate.global.exception.hackathon;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class HackathonWishAlreadyExistsException extends DomainException {
    public HackathonWishAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "HACKATHON_WISH_ALREADY_EXISTS", "이미 관심 등록한 해커톤입니다.");
    }
}
