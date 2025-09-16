package project.management.project_management.dtos;

import project.management.project_management.entities.User;

public class UserMapper {

    public static UserDto toDTO(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }

    public static User toEntity(UserDto dto, String passwordHash) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordHash);
        return user;
    }
}