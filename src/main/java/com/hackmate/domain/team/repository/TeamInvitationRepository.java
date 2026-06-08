package com.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.team.entity.TeamInvitation;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {
}
