package yoon_hyojin.hackmate.exceptions.team;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class TeamMemberNotFoundException extends DomainException {
    public TeamMemberNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEAM_MEMBER_NOT_FOUND", "팀 멤버가 존재하지 않습니다.");
    }
}
