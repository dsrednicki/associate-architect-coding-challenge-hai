package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.TaskEntity;
import java.util.List;
import java.util.Optional;

public interface TaskCrudService {
    TaskEntity create(TaskEntity task);
    Optional<TaskEntity> read(Long id);
    List<TaskEntity> readAll();
    TaskEntity update(TaskEntity task);
    boolean delete(Long id);
}
