package yoon_hyojin.hackmate.repository.hackathon;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.hackathon.Hackathon;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
