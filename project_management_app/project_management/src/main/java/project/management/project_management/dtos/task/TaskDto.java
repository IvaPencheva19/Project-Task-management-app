package project.management.project_management.dtos.task;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Long assignedUserId;
    private String assignedUserEmail;
    private Long columnId;
    private String columnName;
}
