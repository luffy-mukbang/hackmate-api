package yoon_hyojin.hackmate.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
