package com.signavio.architect.challenge.services.generators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signavio.architect.challenge.repository.TaskRepository;
import com.signavio.architect.challenge.repository.UserRepository;
import com.signavio.architect.challenge.repository.entities.TaskEntity;
import com.signavio.architect.challenge.repository.entities.UserEntity;
import com.signavio.architect.challenge.rest.TaskDto;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service("taskGeneratorService")
@DependsOn("userGeneratorService")
public class DefaultTaskGeneratorService implements GeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTaskGeneratorService.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public DefaultTaskGeneratorService(final TaskRepository taskRepository, final UserRepository userRepository, final ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @PostConstruct()
    public List<String> generateSampleData() {
        try {
            final InputStream inputStream = new ClassPathResource("data/sample-data.json").getInputStream();
            final List<TaskDto> tasks = objectMapper
                    .readerFor(new TypeReference<List<TaskDto>>() {})
                    .readValue(inputStream);
            final List<TaskEntity> taskEntities = tasks.stream().map(this::convertToCreate).toList();
            final List<TaskEntity> savedEntities = this.taskRepository.saveAll(taskEntities);
            LOGGER.atInfo().log("Imported {} tasks from JSON", savedEntities.size());
            return savedEntities.stream().map(TaskEntity::getName).collect(Collectors.toList());
        } catch (final IOException ioe) {
            LOGGER.atError().log("Failed to import tasks from JSON: {}", ioe.getMessage());
        }
        return List.of();
    }

    private TaskEntity convertToCreate(final TaskDto taskDto) {
        final TaskEntity task = new TaskEntity();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setCreated(OffsetDateTime.parse(taskDto.getCreated().toString()));
        if (Objects.nonNull(taskDto.getFinished())) {
            task.setFinished(OffsetDateTime.parse(taskDto.getFinished().toString()));
        }
        final Optional<UserEntity> userOpt = userRepository.findByUsername("testuser");
        userOpt.ifPresent(task::setReporter);
        userOpt.ifPresent(task::setAssignee);
        return task;
    }
}
