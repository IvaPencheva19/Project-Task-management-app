package project.management.project_management.dtos.task;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Long assignedUserId;
    private Long columnId;
}