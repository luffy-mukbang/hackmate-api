package com.hackmate.domain.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.hackathon.entity.Hackathon;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
