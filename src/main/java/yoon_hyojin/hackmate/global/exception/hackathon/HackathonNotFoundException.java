package yoon_hyojin.hackmate.global.exception.hackathon;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class HackathonNotFoundException extends DomainException {
    public HackathonNotFoundException() {
        super(HttpStatus.NOT_FOUND, "HACKATHON_NOT_FOUND", "존재하지 않는 해커톤입니다.");
    }
}
