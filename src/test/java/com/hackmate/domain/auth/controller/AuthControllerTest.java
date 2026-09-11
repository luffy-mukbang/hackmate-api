package com.hackmate.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.hackmate.global.config.SecurityConfig;
import com.hackmate.domain.auth.dto.request.*;
import com.hackmate.domain.auth.dto.response.LoginResponse;
import com.hackmate.domain.auth.dto.response.SignupResponse;
import com.hackmate.domain.auth.service.AuthService;
import com.hackmate.domain.auth.service.EmailService;
import com.hackmate.global.auth.TokenProvider;
import com.hackmate.global.auth.CustomUserDetailsService;
import com.hackmate.global.exception.auth.InvalidPasswordException;
import com.hackmate.global.exception.user.UserNotFoundException;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private EmailService emailService;

    @MockitoBean
    private TokenProvider tokenProvider;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("로그인 성공 - 200 OK")
    void login_success() throws Exception {
        given(authService.login(any(LoginRequest.class)))
                .willReturn(new LoginResponse("accessToken", "refreshToken"));

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest("test@test.com", "password"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").value("accessToken"));
    }

    @Test
    @DisplayName("로그인 - 이메일 누락 시 400")
    void login_missingEmail_returns400() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"pw\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 - 잘못된 비밀번호 시 사용자 정의 에러 반환")
    void login_invalidPassword_returnsError() throws Exception {
        given(authService.login(any(LoginRequest.class))).willThrow(new InvalidPasswordException());

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest("test@test.com", "wrong"))))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("회원가입 성공 - 200 OK")
    void signup_success() throws Exception {
        given(authService.signup(any(SignupRequest.class)))
                .willReturn(new SignupResponse(1L));

        String body = objectMapper.writeValueAsString(
                new SignupRequest("new@test.com", "password", "이름", LocalDate.of(2000, 1, 1)));

        mockMvc.perform(post("/auth/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value(1L));
    }

    @Test
    @DisplayName("회원가입 - 이메일 누락 시 400")
    void signup_missingEmail_returns400() throws Exception {
        String body = "{\"password\":\"pw\",\"name\":\"이름\",\"birthDate\":\"2000-01-01\"}";

        mockMvc.perform(post("/auth/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이메일 인증 코드 전송 성공 - 200 OK")
    void sendVerificationCode_success() throws Exception {
        mockMvc.perform(post("/auth/email/send-code")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SendVerificationCodeRequest("test@test.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("이메일 인증 코드 전송 - 잘못된 형식 이메일 시 400")
    void sendVerificationCode_invalidEmail_returns400() throws Exception {
        mockMvc.perform(post("/auth/email/send-code")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이메일 인증 코드 확인 성공 - 200 OK")
    void checkVerificationCode_success() throws Exception {
        mockMvc.perform(post("/auth/email/check-code")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CheckVerificationCodeRequest("test@test.com", "123456"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("닉네임 중복 체크 성공 - 200 OK")
    @WithMockUser
    void checkNickname_available_returns200() throws Exception {
        mockMvc.perform(post("/auth/nickname/check")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CheckNicknameRequest("nick"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("닉네임 중복 체크 - 닉네임 누락 시 400")
    void checkNickname_missing_returns400() throws Exception {
        mockMvc.perform(post("/auth/nickname/check")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nickname\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그아웃 성공 - 200 OK")
    @WithMockUser
    void logOut_success() throws Exception {
        mockMvc.perform(post("/auth/log-out")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("로그아웃 - 인증 없이 접근 시 UserNotFoundException은 401 처리")
    void logOut_notAuthenticated_throwsException() throws Exception {
        doThrow(new UserNotFoundException()).when(authService).logOut();

        mockMvc.perform(post("/auth/log-out")
                        .with(csrf()))
                .andExpect(jsonPath("$.success").value(false));
    }
}
