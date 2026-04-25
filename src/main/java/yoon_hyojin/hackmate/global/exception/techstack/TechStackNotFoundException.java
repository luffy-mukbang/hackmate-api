package yoon_hyojin.hackmate.global.exception.techstack;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class TechStackNotFoundException extends DomainException {
    public TechStackNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TECH_STACK_NOT_FOUND", "존재하지 않는 기술 스택입니다.");
    }
}
