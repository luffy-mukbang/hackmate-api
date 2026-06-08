package com.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.user.entity.RefreshToken;
import com.hackmate.domain.user.entity.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
