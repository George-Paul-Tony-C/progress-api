package in.georgepaultony.progress.auth.controller;

import in.georgepaultony.progress.auth.dto.AuthResponse;
import in.georgepaultony.progress.auth.dto.LoginRequest;
import in.georgepaultony.progress.auth.dto.RegisterRequest;
import in.georgepaultony.progress.auth.dto.UserProfileResponse;
import in.georgepaultony.progress.auth.service.AuthService;
import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request){

        return ResponseUtil.created("User registered successfully" , authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request){

        return ResponseUtil.ok("Login Successfully" , authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> me(){
        return ResponseUtil.ok("User fetched successfully" , authService.getCurrentUser());
    }
}
