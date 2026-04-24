package yoon_hyojin.hackmate.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.team.TeamInvitation;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {
}
