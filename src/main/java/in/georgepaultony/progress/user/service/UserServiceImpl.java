package in.georgepaultony.progress.user.service;

import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.common.exception.BusinessRuleException;
import in.georgepaultony.progress.storage.service.StorageService;
import in.georgepaultony.progress.user.dto.ChangePasswordRequest;
import in.georgepaultony.progress.user.dto.UpdateProfileRequest;
import in.georgepaultony.progress.user.dto.UserResponse;
import in.georgepaultony.progress.user.entity.User;
import in.georgepaultony.progress.user.mapper.UserMapper;
import in.georgepaultony.progress.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Override
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfile(UpdateProfileRequest request){
        String email = currentUserProvider.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(request.getName() != null){
            user.setName(request.getName());
        }

        if(request.getBio() != null){
            user.setBio(request.getBio());
        }

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse uploadAvatar(MultipartFile file) {
        UUID userId = currentUserProvider.getId();

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String folder = "avatars/" + userId;

        String avatarUrl =
                storageService.uploadFile(
                        file,
                        folder
                );

        user.setAvatarUrl(
                avatarUrl
        );

        userRepository.save(
                user
        );

        return UserMapper.toResponse(
                user
        );
    }

    @Override
    public void changePassword(ChangePasswordRequest request){
        String email = currentUserProvider.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean valid = passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPasswordHash()
        );

        if(!valid){
            throw new BusinessRuleException("Current password is incorrect");
        }

        user.setPasswordHash(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
    }

    @Override
    public void deleteCurrentUser(){
        String email = currentUserProvider.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setIsDeleted(true);
        userRepository.save(user);
    }
}
