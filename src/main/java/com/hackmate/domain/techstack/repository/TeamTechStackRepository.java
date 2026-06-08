package com.hackmate.domain.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.techstack.entity.TeamTechStack;

public interface TeamTechStackRepository extends JpaRepository<TeamTechStack, Long> {
}
