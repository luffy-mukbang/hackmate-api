package yoon_hyojin.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
