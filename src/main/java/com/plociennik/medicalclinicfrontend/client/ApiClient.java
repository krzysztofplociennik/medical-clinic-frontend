package com.plociennik.medicalclinicfrontend.client;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Optional.ofNullable;

@Component
public class ApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);
    @Autowired
    private RestTemplate restTemplate;
    @Value("${api.endpoint}")
    private String baseEndpoint;

    private URI getAllReservationsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/rsrv/getReservations")
                .build().encode().toUri();
    }

    private URI getAllDoctorsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/dtr/getDoctors")
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

    public List<DoctorDto> getDoctors() {
        try {
            DoctorDto[] response = restTemplate.getForObject(getAllDoctorsURI(), DoctorDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new DoctorDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
