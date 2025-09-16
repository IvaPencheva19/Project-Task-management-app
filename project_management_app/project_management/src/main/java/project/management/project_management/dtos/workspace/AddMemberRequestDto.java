package project.management.project_management.dtos.workspace;

import lombok.Data;
import project.management.project_management.entities.WorkspaceMember;

@Data
public class AddMemberRequestDto {
    private String email;
    private WorkspaceMember.Role role;
}
