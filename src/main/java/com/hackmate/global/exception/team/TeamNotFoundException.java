package com.hackmate.global.exception.team;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class TeamNotFoundException extends DomainException {
    public TeamNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEAM_NOT_FOUND", "존재하지 않는 팀입니다.");
    }
}
