package yoon_hyojin.hackmate.domain.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.techstack.entity.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
}
