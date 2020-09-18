package com.plociennik.medicalclinicfrontend.logic;

import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionManager {

    private final ApiClient apiClient;

    @Autowired
    public SessionManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserDetails getLoggedInUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public PatientDto getLoggedInUserAsPatient() {
        return apiClient.getPatients().stream()
                .filter(patientDto -> patientDto.getUsername().equals(getLoggedInUser().getUsername()))
                .findAny().get();
    }

    public List<String> getAllRoles() {
        return getLoggedInUser().getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
    }

    public boolean isUser() {
        return getAllRoles().contains("ROLE_USER");
    }

    public boolean isAdmin() {
        return getAllRoles().contains("ROLE_ADMIN");
    }

    public void showAllUsersCredentials() {
        for (PatientDto patient : apiClient.getPatients()) {
            patient.toString();
        }
    }
}
