package yoon_hyojin.hackmate.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AutoLoginRequest {
    private String refreshToken;
}
