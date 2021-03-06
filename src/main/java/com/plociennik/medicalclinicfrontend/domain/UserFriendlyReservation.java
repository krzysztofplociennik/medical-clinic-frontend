package com.plociennik.medicalclinicfrontend.domain;

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

    @Override
    public String toString() {
        return "UserFriendlyReservation{" +
                "doctor='" + doctor + '\'' +
                ", when='" + when + '\'' +
                '}';
    }
}
