package yoon_hyojin.hackmate.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.entity.user.RefreshToken;
import yoon_hyojin.hackmate.entity.user.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
