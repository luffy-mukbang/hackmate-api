package yoon_hyojin.hackmate.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yoon_hyojin.hackmate.domain.user.dto.request.AutoLoginRequest;
import yoon_hyojin.hackmate.domain.user.dto.request.LoginRequest;
import yoon_hyojin.hackmate.domain.user.dto.request.SignupRequest;
import yoon_hyojin.hackmate.domain.user.dto.response.LoginResponse;
import yoon_hyojin.hackmate.domain.user.dto.response.SignupResponse;
import yoon_hyojin.hackmate.domain.user.service.AuthService;
import yoon_hyojin.hackmate.global.response.ApiResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 시 사용하는 API 입니다.")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse result = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(result, "요청이 성공적으로 처리되었습니다."));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 시 사용하는 API 입니다.")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request) {
        SignupResponse result = authService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok(result, "요청이 성공적으로 처리되었습니다."));
    }

    @PostMapping("/auto-login")
    @Operation(summary = "자동 로그인", description = "자동 로그인 시 사용하는 API 입니다.")
    public ResponseEntity<ApiResponse<LoginResponse>> autoLogin(@RequestBody AutoLoginRequest request) {
        LoginResponse result = authService.autoLogin(request);
        return ResponseEntity.ok(ApiResponse.ok(result, "요청이 성공적으로 처리되었습니다."));
    }

    @PostMapping("/log-out")
    @Operation(summary = "로그아웃", description = "로그아웃 시 사용하는 API 입니다.")
    public ResponseEntity<ApiResponse<Void>> logOut() {
        authService.logOut();
        return ResponseEntity.ok(ApiResponse.ok("요청이 성공적으로 처리되었습니다."));
    }
}
