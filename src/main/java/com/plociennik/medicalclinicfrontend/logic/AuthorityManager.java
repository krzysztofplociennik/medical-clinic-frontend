package com.plociennik.medicalclinicfrontend.logic;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityManager {

    private Object loggedInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public Object getLoggedInUser() {
        return loggedInUser;
    }

    public List<String> getAllRoles() {
        return ((UserDetails) loggedInUser).getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
    }

    public boolean isUser() {
        return getAllRoles().contains("ROLE_USER");
    }

    public boolean isMod() {
        return getAllRoles().contains("ROLE_MODERATOR");
    }

    public boolean isAdmin() {
        return getAllRoles().contains("ROLE_ADMIN");
    }


}
