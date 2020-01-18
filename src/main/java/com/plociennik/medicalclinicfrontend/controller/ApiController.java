package com.plociennik.medicalclinicfrontend.controller;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ApiController {
    @Autowired
    private ApiClient apiClient;

    @RequestMapping(method = RequestMethod.GET, value = "getReservations")
    public void getAllReservations() {
        List<ReservationDto> reservationList = apiClient.getReservations();
        reservationList.forEach(reservationDto -> System.out.println(reservationDto.getId() + " " + reservationDto.getTime()));
    }
    @RequestMapping(method = RequestMethod.GET, value = "getDoctors")
    public void getAllDoctors() {
        List<DoctorDto> doctorsList = apiClient.getDoctors();
        doctorsList.forEach(doctorDto -> System.out.println(doctorDto.getName() + " " + doctorDto.getRating()));
    }
}
