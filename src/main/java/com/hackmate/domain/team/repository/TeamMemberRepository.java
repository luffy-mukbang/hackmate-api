package com.hackmate.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.team.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
