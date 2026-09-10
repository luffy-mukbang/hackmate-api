package com.hackmate.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import com.hackmate.domain.auth.dto.request.*;
import com.hackmate.domain.auth.dto.response.LoginResponse;
import com.hackmate.domain.auth.dto.response.SignupResponse;
import com.hackmate.global.response.ApiResponse;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthDocument {

    @Operation(summary = "로그인", description = "로그인 시 사용하는 API 입니다.")
    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);

    @Operation(summary = "회원가입", description = "회원가입 시 사용하는 API 입니다.")
    ResponseEntity<ApiResponse<SignupResponse>> signup(SignupRequest request);

    @Operation(summary = "자동 로그인", description = "자동 로그인 시 사용하는 API 입니다.")
    ResponseEntity<ApiResponse<LoginResponse>> autoLogin(AutoLoginRequest request);

    @Operation(summary = "로그아웃", description = "로그아웃 시 사용하는 API 입니다.")
    ResponseEntity<ApiResponse<Void>> logOut();

    @Operation(summary = "이메일 인증 코드 전송", description = "이메일로 인증 코드를 전송합니다.")
    ResponseEntity<ApiResponse<Void>> sendVerificationCode(SendVerificationCodeRequest request);

    @Operation(summary = "이메일 인증 코드 확인", description = "사용자가 입력한 인증 코드를 확인 및 검증합니다.")
    ResponseEntity<ApiResponse<Void>> checkVerificationCode(CheckVerificationCodeRequest request);

    @Operation(summary = "닉네임 중복 검사", description = "닉네임 중복을 체크합니다.")
    ResponseEntity<ApiResponse<Void>> checkNickname(CheckNicknameRequest request);
}
