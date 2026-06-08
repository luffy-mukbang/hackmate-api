package com.hackmate.global.exception.team;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class TeamInvitationAlreadyRespondedException extends DomainException {
    public TeamInvitationAlreadyRespondedException() {
        super(HttpStatus.CONFLICT, "TEAM_INVITATION_ALREADY_RESPONDED", "이미 처리된 팀 초대입니다.");
    }
}
