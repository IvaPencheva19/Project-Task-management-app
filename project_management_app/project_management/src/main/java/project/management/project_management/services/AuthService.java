package project.management.project_management.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.management.project_management.dtos.auth.AuthResponseDto;
import project.management.project_management.dtos.auth.LoginRequestDto;
import project.management.project_management.dtos.auth.SignupRequestDto;
import project.management.project_management.entities.User;
import project.management.project_management.repositories.UserRepository;
import project.management.project_management.utilities.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDto(token, user.getUsername(), user.getEmail());
    }
}
