package com.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.user.entity.DeveloperProfile;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
}
