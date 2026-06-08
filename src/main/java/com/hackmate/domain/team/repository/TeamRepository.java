package com.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
