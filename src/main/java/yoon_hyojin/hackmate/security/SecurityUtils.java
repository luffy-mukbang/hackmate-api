package yoon_hyojin.hackmate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import yoon_hyojin.hackmate.entity.user.User;
import yoon_hyojin.hackmate.exceptions.auth.NotAuthenticatedException;
import yoon_hyojin.hackmate.exceptions.user.UserNotFoundException;
import yoon_hyojin.hackmate.repository.user.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository usersRepository;

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
