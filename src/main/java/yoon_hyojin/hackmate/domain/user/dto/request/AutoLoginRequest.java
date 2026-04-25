package yoon_hyojin.hackmate.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AutoLoginRequest {
    private String refreshToken;
}
