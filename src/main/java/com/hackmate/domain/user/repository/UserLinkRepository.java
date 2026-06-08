package com.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.user.entity.UserLink;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {
}
