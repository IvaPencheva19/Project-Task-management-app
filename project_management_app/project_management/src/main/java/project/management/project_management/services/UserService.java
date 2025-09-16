package project.management.project_management.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.management.project_management.dtos.UserDto;
import project.management.project_management.dtos.UserMapper;
import project.management.project_management.entities.User;
import project.management.project_management.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // because we set email as "username" in UserDetails
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }


    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ (by ID)
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    // UPDATE
    public Optional<UserDto> updateUser(Long id, User userDetails) { //TODO make check if the user is the same
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
                user.setPasswordHash(userDetails.getPasswordHash());
            }
            User updated = userRepository.save(user);
            return UserMapper.toDTO(updated);
        });
    }

    // DELETE
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}