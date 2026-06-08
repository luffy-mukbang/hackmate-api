package com.hackmate.domain.user.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HtmlLoader {

    public String loadVerificationCodeHtml(String verificationCode) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/verification-code.html");
        String html = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        return html.replace("{{VERIFICATION_CODE}}", verificationCode);
    }
}
