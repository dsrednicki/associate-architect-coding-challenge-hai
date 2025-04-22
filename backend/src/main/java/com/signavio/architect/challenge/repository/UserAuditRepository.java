package com.signavio.architect.challenge.repository;

import com.signavio.architect.challenge.repository.entities.UserAuditEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends JpaRepository<UserAuditEntity, Long> {

    @Query("SELECT item FROM #{#entityName} item LEFT JOIN item.user user WHERE user.username = :username")
    Optional<UserAuditEntity> findByUsername(@Param("username") String username);
}
