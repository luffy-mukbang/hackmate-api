package com.hackmate.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hackmate.domain.user.dto.request.ChangePasswordRequest;
import com.hackmate.domain.user.service.UserService;
import com.hackmate.global.response.ApiResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserDocument {

    private final UserService userService;

    @Override
    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok(ApiResponse.ok("비밀번호가 변경되었습니다."));
    }
}
