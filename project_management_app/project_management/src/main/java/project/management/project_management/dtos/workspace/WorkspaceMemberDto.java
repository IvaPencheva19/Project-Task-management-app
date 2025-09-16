package project.management.project_management.dtos.workspace;


import lombok.Data;

@Data
public class WorkspaceMemberDto {
    private Long id;
    private String username;
    private String email;
    private String role;
}

