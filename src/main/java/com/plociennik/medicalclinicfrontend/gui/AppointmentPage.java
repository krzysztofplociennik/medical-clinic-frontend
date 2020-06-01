package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.plociennik.medicalclinicfrontend.domain.UserFriendlyReservation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Optional;

@Component
@UIScope
public class AppointmentPage extends VerticalLayout {
    private ApiClient apiClient;
    private FormLayout appointmentForm = new FormLayout();
    private Button buttonMakeAppointment = new Button("make an appointment!");
    private Grid<UserFriendlyReservation> gridUserFriendlyList = new Grid<>(UserFriendlyReservation.class);
    private ArrayList<UserFriendlyReservation> friendlyList = new ArrayList<>();
    private ComboBox<DoctorDto> comboBoxDoctor = new ComboBox<>("Doctor");
    private ComboBox<PatientDto> comboBoxPatient = new ComboBox<>("Patient");
    private Button buttonSave = new Button("Save");
    private Button buttonDelete = new Button("Delete");
    private DatePicker datePicker = new DatePicker("Day");
    private TimePicker timePicker = new TimePicker("Time");

    @Autowired
    public AppointmentPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        HorizontalLayout horizontalLayout = new HorizontalLayout(gridUserFriendlyList, appointmentForm);
        horizontalLayout.setSizeFull();

        setupUserFriendlyGrid();
        updateGrid();
        setupAppointmentForm();
        setButtonMakeAppointment();

        add(buttonMakeAppointment, horizontalLayout);
    }

    public void setupUserFriendlyGrid() {
        for (ReservationDto instance : apiClient.getReservations()) {
            Optional<DoctorDto> searchedObject = apiClient.getDoctors().stream().filter(doctorDto -> doctorDto.getId() == instance.getDoctorId()).findFirst();
            friendlyList.add(new UserFriendlyReservation(searchedObject.get().getName(), instance.getTime()));
        }
    }

    public void updateGrid() {
        gridUserFriendlyList.setItems(friendlyList);
        gridUserFriendlyList.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    public void setupAppointmentForm() {
        appointmentForm.setVisible(false);
        HorizontalLayout buttons = new HorizontalLayout(buttonSave, buttonDelete);
        VerticalLayout layout = new VerticalLayout(comboBoxPatient, comboBoxDoctor, datePicker, timePicker, buttons);
        comboBoxDoctor.setItemLabelGenerator(DoctorDto::getName);
        comboBoxDoctor.setItems(apiClient.getDoctors());
        comboBoxPatient.setItemLabelGenerator(PatientDto::getName);
        comboBoxPatient.setItems(apiClient.getPatients());
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        appointmentForm.add(layout);
    }

    public void setButtonMakeAppointment() {
        buttonMakeAppointment.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonMakeAppointment.addClickListener(buttonClickEvent -> appointmentForm.setVisible(true));
    }



}
