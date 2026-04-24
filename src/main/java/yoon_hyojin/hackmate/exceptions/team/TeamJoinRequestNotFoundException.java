package yoon_hyojin.hackmate.exceptions.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class TeamJoinRequestNotFoundException extends DomainException {
    public TeamJoinRequestNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEAM_JOIN_REQUEST_NOT_FOUND", "팀 가입 신청이 존재하지 않습니다.");
    }
}
