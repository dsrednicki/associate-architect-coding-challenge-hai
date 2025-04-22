package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.TaskRepository;
import com.signavio.architect.challenge.repository.entities.TaskEntity;
import com.signavio.architect.challenge.repository.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultTaskService implements TaskCrudService {

    private final TaskRepository taskRepository;
    private final SessionService sessionService;

    public DefaultTaskService(final TaskRepository taskRepository, final SessionService sessionService) {
        this.taskRepository = taskRepository;
        this.sessionService = sessionService;
    }

    @Override
    public Optional<TaskEntity> create(final TaskEntity task) {
        final Optional<UserEntity> currentUserOpt = this.sessionService.getCurrentUser();
        if (currentUserOpt.isEmpty()) {
            return Optional.empty();
        }
        task.setReporter(currentUserOpt.get());
        return Optional.of(this.taskRepository.save(task));
    }

    @Override
    public Optional<TaskEntity> read(final Long id) {
        return this.taskRepository.findById(id);
    }

    @Override
    public List<TaskEntity> readAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public Optional<TaskEntity> update(final TaskEntity task) {
        return Optional.of(this.taskRepository.save(task));
    }

    @Override
    public boolean delete(final Long id) {
        if (this.taskRepository.existsById(id)) {
            this.taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
