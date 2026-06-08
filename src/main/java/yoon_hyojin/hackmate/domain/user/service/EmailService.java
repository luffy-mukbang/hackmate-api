package yoon_hyojin.hackmate.domain.user.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import yoon_hyojin.hackmate.domain.user.repository.UserRepository;
import yoon_hyojin.hackmate.domain.user.utils.HtmlLoader;
import yoon_hyojin.hackmate.domain.user.utils.VerificationCodeGenerator;
import yoon_hyojin.hackmate.global.exception.auth.EmailAlreadyInUseException;
import yoon_hyojin.hackmate.global.exception.auth.VerificationCodeExpiredException;
import yoon_hyojin.hackmate.global.exception.auth.VerificationCodeNotMatchException;
import yoon_hyojin.hackmate.global.redis.RedisService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final UserRepository userRepository;

    public void sendVerificationCode(String email) {
        if (userRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new EmailAlreadyInUseException();
        }

        String verificationCode = VerificationCodeGenerator.generate();
        redisService.saveVerificationCode(email, verificationCode, Duration.ofMinutes(5));

        try {
            String html = HtmlLoader.loadVerificationCodeHtml(verificationCode);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("핵메이트 이메일 인증");
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
    }

    public void checkVerificationCode(String email, String verificationCode) {
        String saved = redisService.getVerificationCode(email);

        if (saved == null) {
            throw new VerificationCodeExpiredException();
        }

        if (!saved.equals(verificationCode)) {
            throw new VerificationCodeNotMatchException();
        }

        redisService.deleteVerificationCode(email);
    }
}
