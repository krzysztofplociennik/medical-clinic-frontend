package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.plociennik.medicalclinicfrontend.domain.UserFriendlyReservation;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@UIScope
public class AppointmentPage extends VerticalLayout {
    private ApiClient apiClient;
    private Button buttonMakeAppointment = new Button("make an appointment!");
    private Text randomText = new Text("Tip: You can freely select and view the appointment you want to examine!");
    private Grid<ReservationDto> gridListOfReservations = new Grid<>(ReservationDto.class);
    private Grid<UserFriendlyReservation> gridUserFriendlyList = new Grid<>(UserFriendlyReservation.class);

    @Autowired
    public AppointmentPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        ArrayList<UserFriendlyReservation> friendlyList = new ArrayList<>();

        for (ReservationDto instance : apiClient.getReservations()) {
            friendlyList.add(new UserFriendlyReservation(instance.getDoctorId(), instance.getTime()));
        }

        buttonMakeAppointment.addThemeVariants(ButtonVariant.LUMO_ICON);
        add(randomText, buttonMakeAppointment, gridUserFriendlyList);
        setSizeFull();
        gridUserFriendlyList.setItems(friendlyList);
    }
}
