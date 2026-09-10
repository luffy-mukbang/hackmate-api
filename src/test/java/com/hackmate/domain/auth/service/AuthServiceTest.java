package com.hackmate.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hackmate.domain.user.dto.request.CheckNicknameRequest;
import com.hackmate.domain.user.dto.request.LoginRequest;
import com.hackmate.domain.user.dto.request.SignupRequest;
import com.hackmate.domain.user.dto.response.LoginResponse;
import com.hackmate.domain.user.dto.response.SignupResponse;
import com.hackmate.domain.user.entity.RefreshToken;
import com.hackmate.domain.user.entity.User;
import com.hackmate.domain.user.repository.RefreshTokenRepository;
import com.hackmate.domain.user.repository.UserRepository;
import com.hackmate.global.auth.TokenProvider;
import com.hackmate.global.auth.security.SecurityUtils;
import com.hackmate.global.exception.auth.InvalidPasswordException;
import com.hackmate.global.exception.auth.NicknameAlreadyInUseException;
import com.hackmate.global.exception.user.UserAlreadyExistsException;
import com.hackmate.global.exception.user.UserNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtils securityUtils;

    private User buildUser(String email, String password) {
        return User.builder()
                .id(1L)
                .email(email)
                .password(password)
                .name("테스터")
                .birthDate(LocalDate.of(2000, 1, 1))
                .isDeleted(false)
                .build();
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        User user = buildUser("test@test.com", "encodedPw");
        given(userRepository.findByEmailAndIsDeletedFalse("test@test.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("rawPw", "encodedPw")).willReturn(true);
        given(tokenProvider.createToken("test@test.com")).willReturn("access");
        given(tokenProvider.createRefreshToken("test@test.com")).willReturn("refresh");

        LoginResponse response = authService.login(new LoginRequest("test@test.com", "rawPw"));

        assertThat(response.getAccessToken()).isEqualTo("access");
        assertThat(response.getRefreshToken()).isEqualTo("refresh");
        verify(refreshTokenRepository).deleteByUser(user);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시 UserNotFoundException")
    void login_userNotFound_throwsException() {
        given(userRepository.findByEmailAndIsDeletedFalse("none@test.com")).willReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(new LoginRequest("none@test.com", "pw")))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("비밀번호 불일치 시 InvalidPasswordException")
    void login_wrongPassword_throwsException() {
        User user = buildUser("test@test.com", "encodedPw");
        given(userRepository.findByEmailAndIsDeletedFalse("test@test.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongPw", "encodedPw")).willReturn(false);

        assertThatThrownBy(() -> authService.login(new LoginRequest("test@test.com", "wrongPw")))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    @DisplayName("OAuth 유저(password=null) 이메일 로그인 시 InvalidPasswordException (NPE 아님)")
    void login_oauthUser_nullPassword_throwsInvalidPassword() {
        User oauthUser = buildUser("oauth@test.com", null);
        given(userRepository.findByEmailAndIsDeletedFalse("oauth@test.com")).willReturn(Optional.of(oauthUser));

        assertThatThrownBy(() -> authService.login(new LoginRequest("oauth@test.com", "anyPw")))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        given(userRepository.findByEmailAndIsDeletedFalse("new@test.com")).willReturn(Optional.empty());
        given(passwordEncoder.encode("rawPw")).willReturn("encodedPw");

        User saved = buildUser("new@test.com", "encodedPw");
        given(userRepository.save(any(User.class))).willReturn(saved);

        SignupResponse response = authService.signup(
                new SignupRequest("new@test.com", "rawPw", "이름", LocalDate.of(2000, 1, 1)));

        assertThat(response.getUserId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 회원가입 시 UserAlreadyExistsException")
    void signup_emailAlreadyExists_throwsException() {
        User existing = buildUser("exist@test.com", "pw");
        given(userRepository.findByEmailAndIsDeletedFalse("exist@test.com")).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> authService.signup(
                new SignupRequest("exist@test.com", "pw", "이름", LocalDate.of(2000, 1, 1))))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logOut_success() {
        User user = buildUser("test@test.com", "pw");
        given(securityUtils.getCurrentUser()).willReturn(user);

        authService.logOut();

        verify(refreshTokenRepository).deleteByUser(user);
    }

    @Test
    @DisplayName("사용 가능한 닉네임 체크 성공")
    void checkNickname_available_noException() {
        given(userRepository.existsByNicknameAndIsDeletedFalse("newNick")).willReturn(false);

        authService.checkNickname(new CheckNicknameRequest("newNick"));
    }

    @Test
    @DisplayName("이미 사용 중인 닉네임 체크 시 NicknameAlreadyInUseException")
    void checkNickname_alreadyInUse_throwsException() {
        given(userRepository.existsByNicknameAndIsDeletedFalse("taken")).willReturn(true);

        assertThatThrownBy(() -> authService.checkNickname(new CheckNicknameRequest("taken")))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }
}
