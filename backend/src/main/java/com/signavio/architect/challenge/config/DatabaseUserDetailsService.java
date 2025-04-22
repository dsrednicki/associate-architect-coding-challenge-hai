package com.signavio.architect.challenge.config;

import com.signavio.architect.challenge.repository.UserAuditRepository;
import com.signavio.architect.challenge.repository.entities.UserAuditEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserAuditRepository userAuditRepository;

    public DatabaseUserDetailsService(final UserAuditRepository userAuditRepository) {
        this.userAuditRepository = userAuditRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserAuditEntity userAudit = userAuditRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User
                .withUsername(username)
                .password(userAudit.getCredential())
                .accountLocked(!userAudit.getAccountNonLocked())
                .accountExpired(!userAudit.getAccountNonExpired())
                .credentialsExpired(!userAudit.getCredentialsNonExpired())
                .roles(userAudit.getGrantedAuthorities().toArray(String[]::new))
                .build();
    }
}
