package project.management.project_management.dtos.auth;


import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String username;
    private String email;

    public AuthResponseDto(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }
}
