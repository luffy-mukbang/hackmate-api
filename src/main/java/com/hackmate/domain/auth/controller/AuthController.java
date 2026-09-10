package com.hackmate.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hackmate.domain.auth.dto.request.*;
import com.hackmate.domain.auth.dto.response.LoginResponse;
import com.hackmate.domain.auth.dto.response.SignupResponse;
import com.hackmate.domain.auth.service.AuthService;
import com.hackmate.domain.auth.service.EmailService;
import com.hackmate.global.response.ApiResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthDocument {

    private final AuthService authService;
    private final EmailService emailService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse result = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(result, "로그인에 성공했습니다."));
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse result = authService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok(result, "회원가입에 성공했습니다."));
    }

    @Override
    @PostMapping("/auto-login")
    public ResponseEntity<ApiResponse<LoginResponse>> autoLogin(@Valid @RequestBody AutoLoginRequest request) {
        LoginResponse result = authService.autoLogin(request);
        return ResponseEntity.ok(ApiResponse.ok(result, "자동 로그인에 성공했습니다."));
    }

    @Override
    @PostMapping("/log-out")
    public ResponseEntity<ApiResponse<Void>> logOut() {
        authService.logOut();
        return ResponseEntity.ok(ApiResponse.ok("로그아웃에 성공했습니다."));
    }

    @Override
    @PostMapping("/email/send-code")
    public ResponseEntity<ApiResponse<Void>> sendVerificationCode(@Valid @RequestBody SendVerificationCodeRequest request) {
        emailService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.ok("이메일 인증 코드 전송에 성공했습니다."));
    }

    @Override
    @PostMapping("/email/check-code")
    public ResponseEntity<ApiResponse<Void>> checkVerificationCode(@Valid @RequestBody CheckVerificationCodeRequest request) {
        emailService.checkVerificationCode(request.getEmail(), request.getVerificationCode());
        return ResponseEntity.ok(ApiResponse.ok("이메일 인증에 성공했습니다."));
    }

    @Override
    @PostMapping("/nickname/check")
    public ResponseEntity<ApiResponse<Void>> checkNickname(@Valid @RequestBody CheckNicknameRequest request) {
        authService.checkNickname(request);
        return ResponseEntity.ok(ApiResponse.ok("사용 가능한 닉네임입니다."));
    }
}
