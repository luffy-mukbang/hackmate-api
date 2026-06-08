package yoon_hyojin.hackmate.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoon_hyojin.hackmate.domain.user.dto.request.AutoLoginRequest;
import yoon_hyojin.hackmate.domain.user.dto.request.CheckNicknameRequest;
import yoon_hyojin.hackmate.domain.user.dto.request.LoginRequest;
import yoon_hyojin.hackmate.domain.user.dto.request.SignupRequest;
import yoon_hyojin.hackmate.domain.user.dto.response.LoginResponse;
import yoon_hyojin.hackmate.domain.user.dto.response.SignupResponse;
import yoon_hyojin.hackmate.domain.user.entity.RefreshToken;
import yoon_hyojin.hackmate.domain.user.entity.User;
import yoon_hyojin.hackmate.domain.user.repository.RefreshTokenRepository;
import yoon_hyojin.hackmate.domain.user.repository.UserRepository;
import yoon_hyojin.hackmate.global.auth.TokenProvider;
import yoon_hyojin.hackmate.global.auth.security.SecurityUtils;
import yoon_hyojin.hackmate.global.exception.auth.ExpiredTokenException;
import yoon_hyojin.hackmate.global.exception.auth.InvalidPasswordException;
import yoon_hyojin.hackmate.global.exception.auth.InvalidTokenException;
import yoon_hyojin.hackmate.global.exception.auth.NicknameAlreadyInUseException;
import yoon_hyojin.hackmate.global.exception.auth.RefreshTokenNotFoundException;
import yoon_hyojin.hackmate.global.exception.user.UserAlreadyExistsException;
import yoon_hyojin.hackmate.global.exception.user.UserNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if (user.getPassword() == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String accessToken = tokenProvider.createToken(user.getEmail());
        String newRefreshToken = tokenProvider.createRefreshToken(user.getEmail());

        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.save(RefreshToken.builder()
                .user(user)
                .token(newRefreshToken)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build());

        return new LoginResponse(accessToken, newRefreshToken);
    }

    public SignupResponse signup(SignupRequest request) {
        if (userRepository.findByEmailAndIsDeletedFalse(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .provider(null)
                .providerId(null)
                .isDeleted(false)
                .build();
        User savedUser = userRepository.save(user);
        return new SignupResponse(savedUser.getId());
    }

    public LoginResponse autoLogin(AutoLoginRequest request) {
        if (!tokenProvider.validateToken(request.getRefreshToken())) {
            throw new InvalidTokenException();
        }

        String email = tokenProvider.getEmail(request.getRefreshToken());
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(UserNotFoundException::new);

        RefreshToken storedToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);

        if (storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException();
        }

        String accessToken = tokenProvider.createToken(email);
        String newRefreshToken = tokenProvider.createRefreshToken(email);

        refreshTokenRepository.delete(storedToken);
        refreshTokenRepository.save(RefreshToken.builder()
                .user(user)
                .token(newRefreshToken)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build());

        return new LoginResponse(accessToken, newRefreshToken);
    }

    public void logOut() {
        User user = securityUtils.getCurrentUser();
        refreshTokenRepository.deleteByUser(user);
    }

    public void checkNickname(CheckNicknameRequest request) {
        if (userRepository.existsByNicknameAndIsDeletedFalse(request.getNickname())) {
            throw new NicknameAlreadyInUseException();
        }
    }
}
