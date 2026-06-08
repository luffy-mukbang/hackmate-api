package com.hackmate.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import com.hackmate.domain.user.dto.request.ChangePasswordRequest;
import com.hackmate.global.response.ApiResponse;

@Tag(name = "User", description = "유저 관련 API")
public interface UserDocument {

    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호 검증 후 새 비밀번호로 변경합니다.")
    ResponseEntity<ApiResponse<Void>> changePassword(ChangePasswordRequest request);
}
