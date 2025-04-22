package com.signavio.architect.challenge.repository;

import com.signavio.architect.challenge.repository.entities.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveAndFindAll() {
        TaskEntity task1 = new TaskEntity();
        task1.setName("test task 1");
        task1.setDescription("my task description 1");
        task1.setCreated(OffsetDateTime.parse("2025-03-18T09:55:18.819Z"));
        task1.setFinished(null);

        TaskEntity task2 = new TaskEntity();
        task2.setName("test task 2");
        task2.setDescription("my task description 2");
        task2.setCreated(OffsetDateTime.parse("2025-03-18T09:55:18.819Z"));
        task2.setFinished(OffsetDateTime.parse("2025-03-18T09:55:18.819Z"));

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<TaskEntity> tasks = taskRepository.findAll();
        assertEquals(2, tasks.size());
        assertEquals("test task 1", tasks.get(0).getName());
        assertEquals("test task 2", tasks.get(1).getName());
    }
}