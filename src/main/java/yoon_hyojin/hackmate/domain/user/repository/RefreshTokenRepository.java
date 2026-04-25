package yoon_hyojin.hackmate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoon_hyojin.hackmate.domain.user.entity.RefreshToken;
import yoon_hyojin.hackmate.domain.user.entity.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
