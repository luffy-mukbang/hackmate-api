package com.hackmate.global.exception.hackathon;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class HackathonWishNotFoundException extends DomainException {
    public HackathonWishNotFoundException() {
        super(HttpStatus.NOT_FOUND, "HACKATHON_WISH_NOT_FOUND", "관심 등록된 해커톤이 아닙니다.");
    }
}
