package yoon_hyojin.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.user.entity.DeveloperProfile;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
}
