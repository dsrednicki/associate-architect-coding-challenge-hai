package com.signavio.architect.challenge.services;

import java.util.List;

import com.signavio.architect.challenge.repository.TaskEntity;
import java.util.UUID;

public interface TaskGeneratorService {
    List<Long> generateSampleTasks();
}
