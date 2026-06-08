package com.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIsDeletedFalse(String email);
    boolean existsByNicknameAndIsDeletedFalse(String nickname);
    Optional<User> findByNickname(String nickname);
}
