package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.TaskEntity;
import com.signavio.architect.challenge.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultTaskServiceTest {

    private DefaultTaskService testInstance;

    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = Mockito.mock(TaskRepository.class);

        this.testInstance = new DefaultTaskService(this.repository);
    }

    @Test
    void given_task_to_create() {
        // GIVEN
        final TaskEntity taskCreated = new TaskEntity();
        Mockito.when(repository.save(taskCreated)).thenReturn(taskCreated);

        // WHEN
        final TaskEntity result = this.testInstance.create(taskCreated);

        // THEN
        assertEquals(taskCreated, result);
    }

    @Test
    void given_task_to_read() {
        // GIVEN
        final Long taskId = 42L;
        final TaskEntity taskCreated = new TaskEntity();
        taskCreated.setId(taskId);
        Mockito.when(repository.findById(taskId)).thenReturn(Optional.of(taskCreated));

        // WHEN
        final Optional<TaskEntity> result = this.testInstance.read(taskId);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(taskId, result.get().getId());
    }

    @Test
    void given_tasks_then_read_all() {
        // GIVEN
        final Long taskId = 42L;
        final TaskEntity taskCreated = new TaskEntity();
        final TaskEntity taskCreated2 = new TaskEntity();
        taskCreated.setId(taskId);
        taskCreated2.setId(taskId+1L);
        Mockito.when(repository.findAll()).thenReturn(List.of(taskCreated, taskCreated2));

        // WHEN
        final List<TaskEntity> result = this.testInstance.readAll();

        // THEN
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(taskId + taskId + 1, result.stream().mapToLong(TaskEntity::getId).sum());
    }

    @Test
    void give_task_to_update() {
        // GIVEN
        final TaskEntity taskUpdated = new TaskEntity();
        Mockito.when(repository.save(taskUpdated)).thenReturn(taskUpdated);

        // WHEN
        final TaskEntity result = this.testInstance.update(taskUpdated);

        // THEN
        assertEquals(taskUpdated, result);
    }

    @Test
    void given_task_to_delete() {
        // GIVEN
        final Long taskId = 42L;
        Mockito.when(repository.existsById(taskId)).thenReturn(true);

        // WHEN
        final boolean result = this.testInstance.delete(taskId);

        // THEN
        assertTrue(result);
        Mockito.verify(repository).deleteById(taskId);
    }

    @Test
    void give_no_task_to_delete() {
        // GIVEN
        final Long taskId = 42L;
        Mockito.when(repository.existsById(taskId)).thenReturn(false);

        // WHEN
        final boolean result = this.testInstance.delete(taskId);

        // THEN
        assertFalse(result);
        Mockito.verify(repository, Mockito.never()).deleteById(taskId);
    }
}