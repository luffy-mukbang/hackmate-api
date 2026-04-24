package yoon_hyojin.hackmate.exceptions.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class NotTeamLeaderException extends DomainException {
    public NotTeamLeaderException() {
        super(HttpStatus.FORBIDDEN, "NOT_TEAM_LEADER", "팀 리더만 수행할 수 있는 작업입니다.");
    }
}
