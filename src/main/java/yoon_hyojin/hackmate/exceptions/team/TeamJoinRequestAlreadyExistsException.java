package yoon_hyojin.hackmate.exceptions.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class TeamJoinRequestAlreadyExistsException extends DomainException {
    public TeamJoinRequestAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "TEAM_JOIN_REQUEST_ALREADY_EXISTS", "이미 가입 신청한 팀입니다.");
    }
}
