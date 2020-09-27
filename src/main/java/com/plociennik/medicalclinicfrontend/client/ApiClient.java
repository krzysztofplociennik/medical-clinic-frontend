package com.plociennik.medicalclinicfrontend.client;
import com.google.gson.Gson;
import com.plociennik.medicalclinicfrontend.domain.*;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static java.util.Optional.ofNullable;

@Component
public class ApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);
    private Gson gson = new Gson();

    @Autowired
    private RestTemplate restTemplate;
    @Value("${api.endpoint}")
    private String baseEndpoint;

    private URI getAllReservationsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/rsrv/getReservations")
                .build().encode().toUri();
    }

    private URI getAllRatingsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/rate/getRatings")
                .build().encode().toUri();
    }

    private URI getAllDoctorsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/dtr/getDoctors")
                .build().encode().toUri();
    }

    private URI getAllPatientsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/pnt/getPatients")
                .build().encode().toUri();
    }

    private URI deletePatientURI(Long id) {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/pnt/deletePatient")
                .queryParam("patientId", id)
                .build().encode().toUri();
    }

    private URI deleteDoctorURI(Long id) {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/dtr/deleteDoctor")
                .queryParam("doctorId", id)
                .build().encode().toUri();
    }

    private URI deleteReservationURI(Long id) {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/rsrv/deleteReservation")
                .queryParam("reservationId", id)
                .build().encode().toUri();
    }

    private URI deleteRatingURI(Long id) {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/rate/deleteRating")
                .queryParam("ratingId", id)
                .build().encode().toUri();
    }

    public List<ReservationDto> getReservations() {
        try {
            ReservationDto[] response = restTemplate.getForObject(getAllReservationsURI(), ReservationDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new ReservationDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    public List<RatingDto> getRatings() {
        try {
            RatingDto[] response = restTemplate.getForObject(getAllRatingsURI(), RatingDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RatingDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<DoctorDto> getDoctors() {
        try {
            DoctorDto[] response = restTemplate.getForObject(getAllDoctorsURI(), DoctorDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new DoctorDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<PatientDto> getPatients() {
        try {
            PatientDto[] response = restTemplate.getForObject(getAllPatientsURI(), PatientDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new PatientDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void updatePatient(PatientDto patientDto) throws IOException {

        String jsonContent = gson.toJson(patientDto);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPut request = new HttpPut(baseEndpoint + "/pnt/updatePatient");
            StringEntity params = new StringEntity(jsonContent);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }

    public void deletePatient(PatientDto patientDto) {
        Optional<PatientDto> searchedPatient = getPatients().stream()
                .filter(patientDto1 -> patientDto1.getId().equals(patientDto.getId()))
                .findFirst();
        restTemplate.delete(deletePatientURI(searchedPatient.get().getId()));
    }

    public void deleteDoctor(DoctorDto doctorDto) {
        Optional<DoctorDto> searchedDoctor = getDoctors().stream()
                .filter(reservationDto -> reservationDto.getId().equals(doctorDto.getId()))
                .findFirst();
        restTemplate.delete(deleteDoctorURI(searchedDoctor.get().getId()));
    }

    public void createReservation(ReservationDto reservationDto) throws IOException {
        ReservationDto reservation = new ReservationDto();
        reservation.setTime(reservationDto.getTime());
        reservation.setPatientId(reservationDto.getPatientId());
        reservation.setDoctorId(reservationDto.getDoctorId());

        String jsonContent = gson.toJson(reservation);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(baseEndpoint + "/rsrv/createReservation");
            StringEntity params = new StringEntity(jsonContent);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }

    public void deleteReservation(UserFriendlyReservation reservation) {
        Optional<ReservationDto> searchedReservation = getReservations().stream()
                .filter(reservationDto -> reservationDto.getTime().equals(reservation.getWhen()))
                .findFirst();
        restTemplate.delete(deleteReservationURI(searchedReservation.get().getId()));
    }

    public void deleteReservation(ReservationDto reservation) {
        Optional<ReservationDto> searchedReservation = getReservations().stream()
                .filter(reservationDto -> reservationDto.getId().equals(reservation.getId()))
                .findFirst();
        restTemplate.delete(deleteReservationURI(searchedReservation.get().getId()));
    }

    public void deleteRating(RatingDto ratingDto) {
        Optional<RatingDto> searchedRating = getRatings().stream()
                .filter(ratingDto1 -> ratingDto1.getId().equals(ratingDto.getId()))
                .findFirst();
        restTemplate.delete(deleteRatingURI(searchedRating.get().getId()));
    }

    public void addNewRating(RatingDto ratingDto) throws IOException {
        RatingDto ratingToConvert = new RatingDto();
        ratingToConvert.setValue(ratingDto.getValue());
        ratingToConvert.setDoctorId(ratingDto.getDoctorId());
        ratingToConvert.setPatientId(ratingDto.getPatientId());
        ratingToConvert.setDateTime(ratingDto.getDateTime());

        String jsonContent = gson.toJson(ratingToConvert);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(baseEndpoint + "/dtr/addRating");
            StringEntity params = new StringEntity(jsonContent);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }
}
