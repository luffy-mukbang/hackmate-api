package yoon_hyojin.hackmate.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.user.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
