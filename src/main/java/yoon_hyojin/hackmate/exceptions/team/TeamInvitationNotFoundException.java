package yoon_hyojin.hackmate.exceptions.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class TeamInvitationNotFoundException extends DomainException {
    public TeamInvitationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEAM_INVITATION_NOT_FOUND", "팀 초대가 존재하지 않습니다.");
    }
}
