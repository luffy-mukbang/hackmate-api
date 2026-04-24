package yoon_hyojin.hackmate.exceptions.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class AlreadyTeamMemberException extends DomainException {
    public AlreadyTeamMemberException() {
        super(HttpStatus.CONFLICT, "ALREADY_TEAM_MEMBER", "이미 해당 팀의 멤버입니다.");
    }
}
