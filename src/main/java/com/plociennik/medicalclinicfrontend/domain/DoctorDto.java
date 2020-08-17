package com.plociennik.medicalclinicfrontend.domain;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

public class DoctorDto {
    private Long id;
    private String name;
    private String mail;
    private String rating;
    private Set<RatingDto> ratings;
    private List<ReservationDto> reservations;

    public DoctorDto(Long id, String name, String mail, String rating, Set<RatingDto> ratings, List<ReservationDto> reservations) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.rating = rating;
        this.ratings = ratings;
        this.reservations = reservations;
    }

    public DoctorDto() {
        ratings = new LinkedHashSet<>();
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

    public String getRating() {
        if (ratings.size() > 0) {
            OptionalDouble optionalRating = ratings.stream().map(RatingDto::getValue).mapToDouble(Double::doubleValue).average();
            rating = String.format("%.2f", optionalRating.getAsDouble());
        } else {
            rating = "not yet rated";
        }
        return rating;
    }

    public Set<RatingDto> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingDto> ratings) {
        this.ratings = ratings;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
}
