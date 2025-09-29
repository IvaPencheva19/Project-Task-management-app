package project.management.project_management.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.management.project_management.dtos.task.TaskDto;
import project.management.project_management.dtos.task.TaskRequestDto;
import project.management.project_management.entities.*;
import project.management.project_management.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;
    private final BoardColumnRepository columnRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProjectRepository projectRepository;

    public TaskDto createTask(Long columnId, TaskRequestDto request) {
        User currentUser = userService.getCurrentUser();

        BoardColumn column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(column.getProject().getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setColumn(column);

        if (request.getAssignedUserId() != null) {
            User assignedUser = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            task.setAssignee(assignedUser);
        }

        task = taskRepository.save(task);

        return toDto(task);
    }

    public List<TaskDto> getTasksByProject(Long projectId) {
        User currentUser = userService.getCurrentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        return taskRepository.findByColumnProjectId(projectId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto updateTask(Long taskId, TaskRequestDto request) {
        User currentUser = userService.getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(task.getColumn().getProject().getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());
        if (request.getAssignedUserId() != null) {
            User assignedUser = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            task.setAssignee(assignedUser);
        }
        if (request.getColumnId() != null) {
            BoardColumn newColumn = columnRepository.findById(request.getColumnId())
                    .orElseThrow(() -> new RuntimeException("Column not found"));
            task.setColumn(newColumn);
        }

        taskRepository.save(task);
        return toDto(task);
    }

    public void deleteTask(Long taskId) {
        User currentUser = userService.getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(task.getColumn().getProject().getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        taskRepository.delete(task);
    }


    private TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        if (task.getAssignee() != null) {
            dto.setAssignedUserId(task.getAssignee().getId());
            dto.setAssignedUserEmail(task.getAssignee().getEmail());
        }
        dto.setColumnId(task.getColumn().getId());
        dto.setColumnName(task.getColumn().getName());
        return dto;
    }
}
