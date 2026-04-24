package yoon_hyojin.hackmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoon_hyojin.hackmate.dto.user.request.AutoLoginRequest;
import yoon_hyojin.hackmate.dto.user.request.LoginRequest;
import yoon_hyojin.hackmate.dto.user.request.SignupRequest;
import yoon_hyojin.hackmate.dto.user.response.LoginResponse;
import yoon_hyojin.hackmate.dto.user.response.SignupResponse;
import yoon_hyojin.hackmate.entity.user.RefreshToken;
import yoon_hyojin.hackmate.entity.user.User;
import yoon_hyojin.hackmate.exceptions.auth.ExpiredTokenException;
import yoon_hyojin.hackmate.exceptions.auth.InvalidPasswordException;
import yoon_hyojin.hackmate.exceptions.auth.InvalidTokenException;
import yoon_hyojin.hackmate.exceptions.auth.RefreshTokenNotFoundException;
import yoon_hyojin.hackmate.exceptions.user.UserAlreadyExistsException;
import yoon_hyojin.hackmate.exceptions.user.UserNotFoundException;
import yoon_hyojin.hackmate.global.TokenProvider;
import yoon_hyojin.hackmate.repository.user.RefreshTokenRepository;
import yoon_hyojin.hackmate.repository.user.UserRepository;
import yoon_hyojin.hackmate.security.SecurityUtils;

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

    // 로그인
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
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

    // 회원가입
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
        userRepository.save(user);
        return new SignupResponse(user.getId());
    }

    // 자동 로그인
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

    // 로그아웃
    public void logOut() {
        User user = securityUtils.getCurrentUser();
        refreshTokenRepository.deleteByUser(user);
    }
}
