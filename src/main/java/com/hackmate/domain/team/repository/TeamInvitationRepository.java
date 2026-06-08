package yoon_hyojin.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.team.entity.TeamInvitation;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {
}
