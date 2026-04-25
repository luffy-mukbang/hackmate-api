package yoon_hyojin.hackmate.domain.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.techstack.entity.TeamTechStack;

public interface TeamTechStackRepository extends JpaRepository<TeamTechStack, Long> {
}
