package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.UserRepository;
import com.signavio.architect.challenge.repository.entities.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository repository;

    public DefaultUserService(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> findUserByUsername(final String username) {
        return repository.findByUsername(username);
    }
}
