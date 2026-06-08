package com.hackmate.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String VERIFICATION_CODE_PREFIX = "email:verification:";

    private final StringRedisTemplate redisTemplate;

    public void saveVerificationCode(String email, String verificationCode, Duration ttl) {
        redisTemplate.opsForValue().set(VERIFICATION_CODE_PREFIX + email, verificationCode, ttl);
    }

    public String getVerificationCode(String email) {
        return redisTemplate.opsForValue().get(VERIFICATION_CODE_PREFIX + email);
    }

    public void deleteVerificationCode(String email) {
        redisTemplate.delete(VERIFICATION_CODE_PREFIX + email);
    }
}
