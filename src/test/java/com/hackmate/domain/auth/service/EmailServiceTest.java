package com.hackmate.domain.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hackmate.domain.user.repository.UserRepository;
import com.hackmate.global.exception.auth.EmailAlreadyInUseException;
import com.hackmate.global.exception.auth.VerificationCodeExpiredException;
import com.hackmate.global.exception.auth.VerificationCodeNotMatchException;
import com.hackmate.global.redis.RedisService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedisService redisService;

    @Test
    @DisplayName("이미 사용 중인 이메일로 인증 코드 요청 시 EmailAlreadyInUseException")
    void sendVerificationCode_emailAlreadyInUse_throwsException() {
        given(userRepository.existsByEmailAndIsDeletedFalse("exist@test.com")).willReturn(true);

        assertThatThrownBy(() -> emailService.sendVerificationCode("exist@test.com"))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }

    @Test
    @DisplayName("인증 코드 확인 성공")
    void checkVerificationCode_success() {
        given(redisService.getVerificationCode("test@test.com")).willReturn("123456");

        emailService.checkVerificationCode("test@test.com", "123456");

        verify(redisService).deleteVerificationCode("test@test.com");
    }

    @Test
    @DisplayName("만료된 인증 코드 확인 시 VerificationCodeExpiredException")
    void checkVerificationCode_expired_throwsException() {
        given(redisService.getVerificationCode("test@test.com")).willReturn(null);

        assertThatThrownBy(() -> emailService.checkVerificationCode("test@test.com", "123456"))
                .isInstanceOf(VerificationCodeExpiredException.class);
    }

    @Test
    @DisplayName("잘못된 인증 코드 입력 시 VerificationCodeNotMatchException")
    void checkVerificationCode_wrong_throwsException() {
        given(redisService.getVerificationCode("test@test.com")).willReturn("123456");

        assertThatThrownBy(() -> emailService.checkVerificationCode("test@test.com", "999999"))
                .isInstanceOf(VerificationCodeNotMatchException.class);
    }
}
