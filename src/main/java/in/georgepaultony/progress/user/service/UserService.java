package in.georgepaultony.progress.user.service;

import in.georgepaultony.progress.user.dto.ChangePasswordRequest;
import in.georgepaultony.progress.user.dto.UpdateProfileRequest;
import in.georgepaultony.progress.user.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUser(UUID id);
    UserResponse updateProfile(UpdateProfileRequest request);
    UserResponse uploadAvatar(MultipartFile file);
    void changePassword(ChangePasswordRequest request);
    void deleteCurrentUser();
}
