package in.georgepaultony.progress.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserProviderImpl implements CurrentUserProvider {

    @Override
    public String getEmail() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    @Override
    public UUID getId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (UUID) authentication.getDetails();
    }
}