package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.entities.UserEntity;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findUserByUsername(String username);
}
