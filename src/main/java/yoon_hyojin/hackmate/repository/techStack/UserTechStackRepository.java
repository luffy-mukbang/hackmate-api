package yoon_hyojin.hackmate.repository.techStack;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.techStack.UserTechStack;

public interface UserTechStackRepository extends JpaRepository<UserTechStack, Long> {
}
