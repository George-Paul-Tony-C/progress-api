package in.georgepaultony.progress.auth.security;

import java.util.UUID;

public interface CurrentUserProvider {
    String getEmail();
    UUID getId();
}
