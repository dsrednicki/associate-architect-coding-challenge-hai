package com.signavio.architect.challenge.services;

import com.signavio.architect.challenge.repository.entities.TaskEntity;
import com.signavio.architect.challenge.repository.TaskRepository;
import com.signavio.architect.challenge.repository.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultTaskServiceTest {

    private DefaultTaskService testInstance;

    private TaskRepository taskRepository;
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        this.taskRepository = Mockito.mock(TaskRepository.class);
        this.sessionService = Mockito.mock(SessionService.class);

        this.testInstance = new DefaultTaskService(this.taskRepository, this.sessionService);
    }

    @Test
    void given_task_to_create() {
        // GIVEN
        final TaskEntity taskCreated = new TaskEntity();
        final UserEntity userCreated = new UserEntity();
        Mockito.when(taskRepository.save(taskCreated)).thenReturn(taskCreated);
        Mockito.when(sessionService.getCurrentUser()).thenReturn(Optional.of(userCreated));

        // WHEN
        final Optional<TaskEntity> result = this.testInstance.create(taskCreated);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(taskCreated, result.get());
    }

    @Test
    void given_task_to_read() {
        // GIVEN
        final Long taskId = 42L;
        final TaskEntity taskCreated = new TaskEntity();
        taskCreated.setId(taskId);
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskCreated));

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
        Mockito.doReturn(List.of(taskCreated, taskCreated2)).when(taskRepository).findAll();

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
        Mockito.when(taskRepository.save(taskUpdated)).thenReturn(taskUpdated);

        // WHEN
        final Optional<TaskEntity> result = this.testInstance.update(taskUpdated);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(taskUpdated, result.get());
    }

    @Test
    void given_task_to_delete() {
        // GIVEN
        final Long taskId = 42L;
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(true);

        // WHEN
        final boolean result = this.testInstance.delete(taskId);

        // THEN
        assertTrue(result);
        Mockito.verify(taskRepository).deleteById(taskId);
    }

    @Test
    void give_no_task_to_delete() {
        // GIVEN
        final Long taskId = 42L;
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(false);

        // WHEN
        final boolean result = this.testInstance.delete(taskId);

        // THEN
        assertFalse(result);
        Mockito.verify(taskRepository, Mockito.never()).deleteById(taskId);
    }
}