package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.plociennik.medicalclinicfrontend.domain.UserFriendlyReservation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Optional;

@Component
@UIScope
public class AppointmentPage extends VerticalLayout {
    private ApiClient apiClient;
    private Button buttonMakeAppointment = new Button("make an appointment!");
    private Grid<UserFriendlyReservation> gridUserFriendlyList = new Grid<>(UserFriendlyReservation.class);

    @Autowired
    public AppointmentPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        ArrayList<UserFriendlyReservation> friendlyList = new ArrayList<>();

        for (ReservationDto instance : apiClient.getReservations()) {
            Optional<DoctorDto> searchedObject = apiClient.getDoctors().stream().filter(doctorDto -> doctorDto.getId() == instance.getDoctorId()).findFirst();
            friendlyList.add(new UserFriendlyReservation(searchedObject.get().getName(), instance.getTime()));
        }

        buttonMakeAppointment.addThemeVariants(ButtonVariant.LUMO_ICON);
        gridUserFriendlyList.setItems(friendlyList);
        add(buttonMakeAppointment, gridUserFriendlyList);
    }

}
