package yoon_hyojin.hackmate.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import javax.annotation.security.DenyAll;
import java.time.LocalDate;


@Getter
@DenyAll
public class SignupRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "생년월일을 입력해주세요.")
    private LocalDate birthDate;
}
