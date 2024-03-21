package team.gsmgogo.global.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityAdapter {
    public Long getCurrentUserId(){
        return Long.getLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
