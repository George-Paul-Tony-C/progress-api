package in.georgepaultony.progress.user.controller;

import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import in.georgepaultony.progress.user.dto.ChangePasswordRequest;
import in.georgepaultony.progress.user.dto.UpdateProfileRequest;
import in.georgepaultony.progress.user.dto.UserResponse;
import in.georgepaultony.progress.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        return ResponseUtil.ok("Users fetched successfully" , userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable UUID id){

        return ResponseUtil.ok("User fetched successfully" , userService.getUser(id));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@RequestBody UpdateProfileRequest request){
        return ResponseUtil.ok("Profile updated successfully" , userService.updateProfile(request));
    }

    @PostMapping(value = "/me/avatar", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<UserResponse>> uploadAvatar(@RequestPart("file") MultipartFile file){
        return ResponseUtil.ok("Avatar uploaded successfully", userService.uploadAvatar(file)
        );
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody ChangePasswordRequest request){
        userService.changePassword(request);
        return ResponseUtil.ok("Password changed successfully" , null);
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteCurrentUser(){
        userService.deleteCurrentUser();
        return ResponseUtil.ok("Account deleted successfully" , null);
    }
}
