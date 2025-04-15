package com.signavio.architect.challenge.rest;

import com.signavio.architect.challenge.repository.TaskEntity;
import com.signavio.architect.challenge.services.TaskCrudService;
import com.signavio.architect.challenge.services.TaskGeneratorService;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private final TaskCrudService taskService;
    private final TaskGeneratorService taskGeneratorService;

    @Autowired
    public TaskController(final TaskCrudService taskService, final TaskGeneratorService taskGeneratorService) {
        this.taskService = taskService;
        this.taskGeneratorService = taskGeneratorService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody final TaskDto task) {
        final TaskEntity taskEntity = this.taskService.create(Optional.of(task).map(this::convertToCreateEntity).get());
        if (Objects.isNull(taskEntity)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(this.convertToDto(taskEntity));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        final List<TaskEntity> all = taskService.readAll();
        final List<TaskDto> tasks = all.stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getATask(@PathVariable @Positive final long id) {
        final Optional<TaskDto> task = taskService.read(id).map(this::convertToDto);
        return ResponseEntity.of(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable  @Positive final long id, @RequestBody final TaskDto task) {
        final TaskEntity taskEntityToUpdate = Optional.of(task).map(this::convertToEntity).get();
        taskEntityToUpdate.setId(id);
        final TaskEntity taskEntity = this.taskService.update(taskEntityToUpdate);
        if (Objects.isNull(taskEntity)) {
            return ResponseEntity.notFound().build();
        }
        final TaskDto taskDto = this.convertToDto(taskEntity);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Positive final long id) {
        if (this.taskService.delete(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    @Profile("test")
    @PostMapping("/add-sample-data")
    public ResponseEntity<List<Long>> createSampleTasks() {
        final List<Long> taskIds = this.taskGeneratorService.generateSampleTasks();
        final URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/tasks")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(taskIds);
    }

    private TaskDto convertToDto(final TaskEntity taskEntity) {
        final TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setName(taskEntity.getName());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setCreated(taskEntity.getCreated().toZonedDateTime());
        taskDto.setFinished(taskEntity.getFinished() != null ? taskEntity.getFinished().toZonedDateTime() : null);
        return taskDto;
    }

    private TaskEntity convertToEntity(final TaskDto task) {
        final TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setCreated(task.getCreated().toOffsetDateTime());
        taskEntity.setFinished(task.getFinished() != null ? task.getFinished().toOffsetDateTime() : null);
        return taskEntity;
    }

    private TaskEntity convertToCreateEntity(final TaskDto task) {
        final TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setCreated(Objects.isNull(task.getCreated()) ? OffsetDateTime.now() : task.getCreated().toOffsetDateTime());
        taskEntity.setFinished(Objects.nonNull(task.getFinished()) ? task.getFinished().toOffsetDateTime() :  null);
        return taskEntity;
    }

}
