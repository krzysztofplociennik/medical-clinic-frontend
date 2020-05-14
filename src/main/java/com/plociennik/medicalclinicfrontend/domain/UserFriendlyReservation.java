package com.plociennik.medicalclinicfrontend.domain;
import java.time.LocalDateTime;

public class UserFriendlyReservation {
    private String doctor;
    private LocalDateTime when;

    public UserFriendlyReservation(String doctor, LocalDateTime when) {
        this.doctor = doctor;
        this.when = when;
    }

    public UserFriendlyReservation() {
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }
}
