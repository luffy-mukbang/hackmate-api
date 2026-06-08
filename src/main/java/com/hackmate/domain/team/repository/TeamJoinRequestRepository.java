package yoon_hyojin.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.team.entity.TeamJoinRequest;

public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, Long> {
}
