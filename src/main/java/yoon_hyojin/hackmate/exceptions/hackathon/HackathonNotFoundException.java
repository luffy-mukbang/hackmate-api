package yoon_hyojin.hackmate.exceptions.hackathon;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class HackathonNotFoundException extends DomainException {
    public HackathonNotFoundException() {
        super(HttpStatus.NOT_FOUND, "HACKATHON_NOT_FOUND", "존재하지 않는 해커톤입니다.");
    }
}
