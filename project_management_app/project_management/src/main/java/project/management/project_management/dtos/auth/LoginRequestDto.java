package project.management.project_management.dtos.auth;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
