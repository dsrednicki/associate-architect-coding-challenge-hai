package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.TaskEntity;
import com.signavio.architect.challenge.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultTaskService implements TaskCrudService {

    private final TaskRepository repository;

    public DefaultTaskService(final TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskEntity create(final TaskEntity task) {
        return this.repository.save(task);
    }

    @Override
    public Optional<TaskEntity> read(final Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<TaskEntity> readAll() {
        return this.repository.findAll();
    }

    @Override
    public TaskEntity update(final TaskEntity task) {
        return this.repository.save(task);
    }

    @Override
    public boolean delete(final Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }

}
