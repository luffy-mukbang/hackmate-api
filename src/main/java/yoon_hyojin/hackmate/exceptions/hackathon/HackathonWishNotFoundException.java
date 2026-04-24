package yoon_hyojin.hackmate.exceptions.hackathon;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class HackathonWishNotFoundException extends DomainException {
    public HackathonWishNotFoundException() {
        super(HttpStatus.NOT_FOUND, "HACKATHON_WISH_NOT_FOUND", "관심 등록된 해커톤이 아닙니다.");
    }
}
