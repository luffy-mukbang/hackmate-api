package com.hackmate.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckNicknameRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
