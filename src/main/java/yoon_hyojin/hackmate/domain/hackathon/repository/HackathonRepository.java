package yoon_hyojin.hackmate.domain.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.hackathon.entity.Hackathon;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
