package com.signavio.architect.challenge.rest;

import com.signavio.architect.challenge.repository.TaskEntity;
import com.signavio.architect.challenge.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() throws Exception {
        setupTestData();
        String expectedJson = """
                {
                  "tasks": [
                    {
                      "name": "test task",
                      "description": "my task description",
                      "created": "2025-03-18T09:55:18.819Z",
                      "finished": null
                    },
                    {
                      "name": "test task",
                      "description": "my task description",
                      "created": "2025-03-18T09:55:18.819Z",
                      "finished": "2025-03-18T09:55:18.819Z"
                    }
                  ]
                }
                """;

        mockMvc.perform(get("/tasks")).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    private void setupTestData() {
        TaskEntity task1 = new TaskEntity();
        task1.setName("test task");
        task1.setDescription("my task description");
        task1.setCreated(ZonedDateTime.parse("2025-03-18T09:55:18.819Z").toOffsetDateTime());
        task1.setFinished(null);
        taskRepository.save(task1);

        TaskEntity task2 = new TaskEntity();
        task2.setName("test task");
        task2.setDescription("my task description");
        task2.setCreated(ZonedDateTime.parse("2025-03-18T09:55:18.819Z").toOffsetDateTime());
        task2.setFinished(ZonedDateTime.parse("2025-03-18T09:55:18.819Z").toOffsetDateTime());
        taskRepository.save(task2);

    }
}