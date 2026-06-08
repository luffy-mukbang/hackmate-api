package com.hackmate.domain.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.techstack.entity.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
}
