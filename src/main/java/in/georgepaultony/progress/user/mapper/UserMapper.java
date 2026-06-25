package in.georgepaultony.progress.user.mapper;

import in.georgepaultony.progress.user.dto.UserResponse;
import in.georgepaultony.progress.user.entity.User;

public class UserMapper {
    private UserMapper(){}

    public static UserResponse toResponse(User entity){
        UserResponse response = new UserResponse();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        response.setBio(entity.getBio());
        response.setAvatarUrl(entity.getAvatarUrl());

        return response;
    }
}
