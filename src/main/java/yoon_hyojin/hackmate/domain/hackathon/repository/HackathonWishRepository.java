package yoon_hyojin.hackmate.domain.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.hackathon.entity.HackathonWish;

public interface HackathonWishRepository extends JpaRepository<HackathonWish, Long> {
}
