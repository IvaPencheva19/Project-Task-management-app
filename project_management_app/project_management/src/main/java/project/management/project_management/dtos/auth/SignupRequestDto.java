package project.management.project_management.dtos.auth;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;
}
