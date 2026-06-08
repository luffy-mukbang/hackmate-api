package yoon_hyojin.hackmate.global.exception.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class TeamJoinRequestAlreadyRespondedException extends DomainException {
    public TeamJoinRequestAlreadyRespondedException() {
        super(HttpStatus.CONFLICT, "TEAM_JOIN_REQUEST_ALREADY_RESPONDED", "이미 처리된 가입 신청입니다.");
    }
}
