package project.management.project_management.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.management.project_management.dtos.task.TaskDto;
import project.management.project_management.dtos.task.TaskRequestDto;
import project.management.project_management.services.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/api/columns/{columnId}/tasks")
    public ResponseEntity<TaskDto> createTask(@PathVariable Long columnId,
                                              @RequestBody TaskRequestDto request) {
        return ResponseEntity.ok(taskService.createTask(columnId, request));
    }

    @GetMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @PutMapping("/api/tasks/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId,
                                              @RequestBody TaskRequestDto request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    @DeleteMapping("/api/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

}
