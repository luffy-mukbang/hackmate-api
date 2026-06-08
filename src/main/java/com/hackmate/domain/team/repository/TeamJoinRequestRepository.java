package com.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.team.entity.TeamJoinRequest;

public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, Long> {
}
