package yoon_hyojin.hackmate.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);
}
