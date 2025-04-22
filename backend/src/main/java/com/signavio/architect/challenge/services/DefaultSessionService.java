package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.entities.UserEntity;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DefaultSessionService implements SessionService {

    private final UserService userService;

    public DefaultSessionService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserEntity> getCurrentUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userService.findUserByUsername(userDetails.getUsername());
        }
        return userService.findUserByUsername(principal.toString());
    }

}
