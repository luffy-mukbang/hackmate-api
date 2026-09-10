package com.hackmate.domain.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.hackathon.entity.HackathonWish;

import java.util.Optional;

public interface HackathonWishRepository extends JpaRepository<HackathonWish, Long> {

    boolean existsByUserIdAndHackathonId(Long userId, Long hackathonId);

    Optional<HackathonWish> findByUserIdAndHackathonId(Long userId, Long hackathonId);
}
