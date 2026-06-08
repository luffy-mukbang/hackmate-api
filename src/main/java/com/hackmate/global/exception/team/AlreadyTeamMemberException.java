package com.hackmate.global.exception.team;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class AlreadyTeamMemberException extends DomainException {
    public AlreadyTeamMemberException() {
        super(HttpStatus.CONFLICT, "ALREADY_TEAM_MEMBER", "이미 해당 팀의 멤버입니다.");
    }
}
