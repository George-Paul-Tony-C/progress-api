package in.georgepaultony.progress.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserProfileResponse {
    private UUID id;
    private String name;
    private String email;
}
