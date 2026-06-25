package in.georgepaultony.progress.auth.service;

import in.georgepaultony.progress.auth.dto.AuthResponse;
import in.georgepaultony.progress.auth.dto.LoginRequest;
import in.georgepaultony.progress.auth.dto.RegisterRequest;
import in.georgepaultony.progress.auth.dto.UserProfileResponse;
import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.auth.security.JwtService;
import in.georgepaultony.progress.common.exception.DuplicateResourceException;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.user.entity.User;
import in.georgepaultony.progress.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public AuthResponse register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateResourceException("Email already exist");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved);

        return AuthResponse.builder()
                .accessToken(token)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not exist or Invalid Email"));

        boolean valid = passwordEncoder.matches(request.getPassword() , user.getPasswordHash());

        if(!valid) {
            throw new ResourceNotFoundException("Invalid Password");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    @Override
    public UserProfileResponse getCurrentUser(){
        String email = currentUserProvider.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
