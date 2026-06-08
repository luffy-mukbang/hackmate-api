package com.hackmate.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hackmate.domain.user.dto.request.ChangePasswordRequest;
import com.hackmate.domain.user.entity.User;
import com.hackmate.global.auth.security.SecurityUtils;
import com.hackmate.global.exception.auth.InvalidPasswordException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePasswordRequest request) {
        User user = securityUtils.getCurrentUser();

        if (user.getPassword() == null || !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }
}
