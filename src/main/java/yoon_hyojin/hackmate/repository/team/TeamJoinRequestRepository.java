package yoon_hyojin.hackmate.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.team.TeamJoinRequest;

public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, Long> {
}
