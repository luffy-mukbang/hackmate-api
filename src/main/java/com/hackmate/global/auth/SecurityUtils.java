package com.hackmate.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.hackmate.domain.user.entity.User;
import com.hackmate.domain.user.repository.UserRepository;
import com.hackmate.global.exception.auth.NotAuthenticatedException;
import com.hackmate.global.exception.user.UserNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository usersRepository;

    public Optional<User> getCurrentUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
            return usersRepository.findByEmailAndIsDeletedFalse(springUser.getUsername());
        }
        return Optional.empty();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotAuthenticatedException();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
            return usersRepository.findByEmailAndIsDeletedFalse(springUser.getUsername())
                    .orElseThrow(UserNotFoundException::new);
        } else {
            throw new NotAuthenticatedException();
        }
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
