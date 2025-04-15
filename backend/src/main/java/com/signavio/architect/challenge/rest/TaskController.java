package com.signavio.architect.challenge.rest;

import com.signavio.architect.challenge.repository.TaskEntity;
import com.signavio.architect.challenge.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public Map<String, List<TaskDto>> getAllTasks() {
        List<TaskEntity> all = taskRepository.findAll();
        List<TaskDto> tasks = all.stream().map(this::convertToDto).toList();
        return Map.of("tasks", tasks);
    }

    private TaskDto convertToDto(TaskEntity taskEntity) {
        TaskDto taskDto = new TaskDto();
        taskDto.setName(taskEntity.getName());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setCreated(taskEntity.getCreated().toZonedDateTime());
        taskDto.setFinished(taskEntity.getFinished() != null ? taskEntity.getFinished().toZonedDateTime() : null);
        return taskDto;
    }


}
