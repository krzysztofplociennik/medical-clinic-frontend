package com.plociennik.medicalclinicfrontend.domain;
import java.time.LocalDateTime;

public class UserFriendlyReservation {
    private String doctor;
    private String when;

    public UserFriendlyReservation(String doctor, String when) {
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

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }
}
