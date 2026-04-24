package yoon_hyojin.hackmate.exceptions.techstack;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class TechStackAlreadyExistsException extends DomainException {
    public TechStackAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "TECH_STACK_ALREADY_EXISTS", "이미 존재하는 기술 스택입니다.");
    }
}
