package yoon_hyojin.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.user.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
