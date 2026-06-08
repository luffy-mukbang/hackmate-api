package com.hackmate.domain.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.techstack.entity.UserTechStack;

public interface UserTechStackRepository extends JpaRepository<UserTechStack, Long> {
}
