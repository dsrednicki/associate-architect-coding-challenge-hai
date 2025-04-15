package com.signavio.architect.challenge.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.signavio.architect.challenge.repository.TaskEntity;
import com.signavio.architect.challenge.repository.TaskRepository;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        taskRepository.deleteAll();
    }

    @Test
    public void testCreateATask() throws Exception {
        // WHEN
        final MvcResult mvcResult = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "test task",
                          "description": "my task description",
                          "created": "2025-03-18T09:55:18.819Z",
                          "finished": ""
                        }
                        """)).andExpect(status().isCreated()).andReturn();

        // THEN
        assertTrue(getTaskDto(mvcResult).getId() > 0);
    }

    @Test
    public void testGetAllTasks() throws Exception {
        setupTestData();
        String expectedJson = """
               [
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
              """;

        mockMvc.perform(get("/tasks")).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    public void testGetATask() throws Exception {
        // GIVEN
        final MvcResult mvcResult = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "test task",
                          "description": "my task description",
                          "created": "2025-03-18T09:55:18.819Z",
                          "finished": ""
                        }
                        """)).andExpect(status().isCreated()).andReturn();
        final long id =  getTaskDto(mvcResult).getId();
        String expectedJson = """
                {
                  "name": "test task",
                  "description": "my task description",
                  "created": "2025-03-18T09:55:18.819Z",
                  "finished": null
                }
                """;

        // WHEN / THEN
        mockMvc.perform(get("/tasks/" + id)).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    public void testUpdateATask() throws Exception {
        // GIVEN
        final MvcResult mvcResult = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "test task",
                          "description": "my task description",
                          "created": "2025-03-18T09:55:18.819Z",
                          "finished": ""
                        }
                        """)).andExpect(status().isCreated()).andReturn();
        final long id =  getTaskDto(mvcResult).getId();
        final String expectedJson = """
                {
                  "name": "test task",
                  "description": "my task description",
                  "created": "2025-03-18T09:55:18.819Z",
                  "finished": "2025-03-18T09:55:18.819Z"
                }
                """;

        // WHEN / THEN
        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson)).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    public void testDeleteATask() throws Exception {
        // GIVEN
        setupTestData();

        // WHEN / THEN
        mockMvc.perform(delete("/tasks/1")).andExpect(status().isNoContent());
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

    private TaskDto getTaskDto(final MvcResult mvcResult) throws IOException {
        return this.objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), TaskDto.class);
    }
}