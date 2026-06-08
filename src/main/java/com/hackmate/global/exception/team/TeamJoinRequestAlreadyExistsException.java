package com.hackmate.global.exception.team;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class TeamJoinRequestAlreadyExistsException extends DomainException {
    public TeamJoinRequestAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "TEAM_JOIN_REQUEST_ALREADY_EXISTS", "이미 가입 신청한 팀입니다.");
    }
}
