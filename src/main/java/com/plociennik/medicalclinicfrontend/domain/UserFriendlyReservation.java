package com.plociennik.medicalclinicfrontend.domain;

import java.time.LocalDateTime;

public class UserFriendlyReservation {
    private Long doctor;
    private LocalDateTime when;

    public UserFriendlyReservation(Long doctor, LocalDateTime when) {
        this.doctor = doctor;
        this.when = when;
    }

    public UserFriendlyReservation() {
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }
}
