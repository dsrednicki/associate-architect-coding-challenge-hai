package com.signavio.architect.challenge.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InMemoryDatabaseUserDetailsService implements UserDetailsService {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "password123";
    private static final String USER_ROLE = "USER";

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return User.builder()
                .username(USERNAME)
                .password(new BCryptPasswordEncoder().encode(PASSWORD))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .roles(USER_ROLE)
                .build();
    }
}
