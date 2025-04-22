package com.signavio.architect.challenge.services.generators;

import com.signavio.architect.challenge.config.AppProperties;
import com.signavio.architect.challenge.repository.UserAuditRepository;
import com.signavio.architect.challenge.repository.entities.UserAuditEntity;
import com.signavio.architect.challenge.repository.entities.UserEntity;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userGeneratorService")
public class DefaultUserGeneratorService implements GeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserGeneratorService.class);

    public static final String  DEFAULT_USERNAME = "testuser";
    private static final String DEFAULT_FIRSTNAME = "Tester";
    private static final String DEFAULT_LASTNAME = "tests";
    public static final String DEFAULT_PASSWORD = "password123";
    public static final String DEFAULT_ROLES = "USER";

    private final UserAuditRepository userAuditRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    public DefaultUserGeneratorService(final UserAuditRepository userAuditRepository, final PasswordEncoder passwordEncoder, final AppProperties appProperties) {
        this.userAuditRepository = userAuditRepository;
        this.passwordEncoder = passwordEncoder;
        this.appProperties = appProperties;
    }

    @Override
    @PostConstruct
    public List<String> generateSampleData() {
        final List<Pair<UserEntity, UserAuditEntity>> userCredsPairs = new ArrayList<>(getSampleUserPairs());
        final List<UserAuditEntity> userAuditEntities = userCredsPairs.stream().map(Pair::getSecond).toList();
        final List<UserAuditEntity> savedEntities = this.userAuditRepository.saveAll(userAuditEntities);
        LOGGER.atInfo().log("Imported {} users!", savedEntities.size());
        return savedEntities.stream().map(entity -> entity.getUser().getUsername()).collect(Collectors.toList());
    }

    private void addFixedSampleUser(final List<Pair<UserEntity, UserAuditEntity>> userCredsPairs) {
        final Pair<UserEntity, UserAuditEntity> userCredsPair = getSampleUserPair(
                DEFAULT_USERNAME,
                DEFAULT_FIRSTNAME,
                DEFAULT_LASTNAME,
                DEFAULT_PASSWORD,
                DEFAULT_ROLES
        );
        userCredsPairs.add(userCredsPair);
    }

    public String generateASampleData(final AppProperties.UserProperties userProperties) {
        final Pair<UserEntity, UserAuditEntity> userCredsPair = getSampleUserPair(
                userProperties.getUsername(),
                userProperties.getFirstName(),
                userProperties.getLastName(),
                userProperties.getPassword(),
                userProperties.getRoles()
        );
        final UserAuditEntity savedEntity = this.userAuditRepository.save(userCredsPair.getSecond());
        return savedEntity.getUser().getUsername();
    }

    private List<Pair<UserEntity, UserAuditEntity>> getSampleUserPairs() {
        return appProperties.getUsers().stream().map( user ->
                getSampleUserPair(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPassword(),
                        user.getRoles())
        ).toList();
    }

    private Pair<UserEntity, UserAuditEntity> getSampleUserPair(
            final String username,
            final String firstName,
            final String lastName,
            final String password,
            final String roles
    ) {
        final UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        final UserAuditEntity userAudit = new UserAuditEntity();
        userAudit.setUser(user);
        userAudit.setCredential(passwordEncoder.encode(password));
        userAudit.setAccountNonLocked(true);
        userAudit.setAccountNonExpired(true);
        userAudit.setCredentialsNonExpired(true);
        userAudit.setGrantedAuthorities(Set.of(roles.split(",")));

        return Pair.of(user, userAudit);
    }

}
