package com.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.user.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
