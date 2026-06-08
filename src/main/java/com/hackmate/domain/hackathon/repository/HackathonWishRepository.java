package com.hackmate.domain.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.hackathon.entity.HackathonWish;

public interface HackathonWishRepository extends JpaRepository<HackathonWish, Long> {
}
