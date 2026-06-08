package com.hackmate.global.exception.team;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class TeamInvitationNotFoundException extends DomainException {
    public TeamInvitationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEAM_INVITATION_NOT_FOUND", "팀 초대가 존재하지 않습니다.");
    }
}
