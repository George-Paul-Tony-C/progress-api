package in.georgepaultony.progress.auth.service;

import in.georgepaultony.progress.auth.dto.AuthResponse;
import in.georgepaultony.progress.auth.dto.LoginRequest;
import in.georgepaultony.progress.auth.dto.RegisterRequest;
import in.georgepaultony.progress.auth.dto.UserProfileResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserProfileResponse getCurrentUser();
}
