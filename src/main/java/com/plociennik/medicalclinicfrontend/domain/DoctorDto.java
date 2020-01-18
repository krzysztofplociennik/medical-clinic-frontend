package com.plociennik.medicalclinicfrontend.domain;

import java.util.List;

public class DoctorDto {
    private Long id;
    private String name;
    private String mail;
    private Double rating;
    private List<ReservationDto> reservations;

    public DoctorDto(Long id, String name, String mail, Double rating, List<ReservationDto> reservations) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.rating = rating;
        this.reservations = reservations;
    }

    public DoctorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
}
