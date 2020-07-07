package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.plociennik.medicalclinicfrontend.domain.UserFriendlyReservation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private Button buttonCancel = new Button("cancel");
    private DatePicker datePicker = new DatePicker("Day");
    private TimePicker timePicker = new TimePicker("Time");
    private HorizontalLayout buttonsTopLayout = new HorizontalLayout();
    private HorizontalLayout gridAndFormLayout = new HorizontalLayout();
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm";

    @Autowired
    public AppointmentPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        buttonsTopLayout.add(buttonMakeAppointment, buttonCancel);
        gridAndFormLayout.add(gridUserFriendlyList, appointmentForm);
        gridAndFormLayout.setSizeFull();

        setupUserFriendlyGrid();
        setupAppointmentForm();
        setupButtons();

        add(buttonsTopLayout, gridAndFormLayout);
    }

    public void setupUserFriendlyGrid() {
        friendlyList = new ArrayList<>();
        for (ReservationDto instance : apiClient.getReservations()) {
            Optional<DoctorDto> searchedObject = apiClient.getDoctors().stream().filter(doctorDto -> doctorDto.getId() == instance.getDoctorId()).findFirst();
            friendlyList.add(new UserFriendlyReservation(searchedObject.get().getName(), instance.getTime()));
        }
        gridUserFriendlyList.setItems(friendlyList);
        gridUserFriendlyList.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        gridUserFriendlyList.asSingleSelect().addValueChangeListener(event -> {
            appointmentForm.setVisible(true);
            buttonCancel.setVisible(true);
            UserFriendlyReservation selectedRecord = gridUserFriendlyList.asSingleSelect().getValue();
            buttonDelete.addClickListener(buttonClickEvent -> {
                deleteEvent(selectedRecord);
            });
        });
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
        buttonSave.addClickListener(buttonClickEvent -> {
            try {
                saveEvent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        buttonDelete.addClickListener(buttonClickEvent -> {
//            deleteEvent(gridUserFriendlyList.asSingleSelect().getValue());
//        });
        appointmentForm.add(layout);
    }

    public void setupButtons() {
        buttonMakeAppointment.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonMakeAppointment.addClickListener(buttonClickEvent -> {
            appointmentForm.setVisible(true);
            buttonCancel.setVisible(true);
        });
        buttonCancel.setVisible(false);
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonCancel.addClickListener(buttonClickEvent -> {
            appointmentForm.setVisible(false);
            buttonCancel.setVisible(false);
        });
    }

    public void saveEvent() throws IOException {
        ReservationDto reservation = new ReservationDto();
        LocalDate date = datePicker.getValue();
        LocalTime time = timePicker.getValue();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        reservation.setTime(dateTime.format(formatter));
        reservation.setPatientId(comboBoxPatient.getValue().getId());
        reservation.setDoctorId(comboBoxDoctor.getValue().getId());
        apiClient.createReservation(reservation);
        setupUserFriendlyGrid();
        comboBoxPatient.setValue(null);
        comboBoxDoctor.setValue(null);
        datePicker.setValue(null);
        timePicker.setValue(null);
    }

    public void deleteEvent(UserFriendlyReservation reservation) {
        System.out.println("Before deleting: " + friendlyList.size());
        Optional<UserFriendlyReservation> searchedReservation = Optional.of(friendlyList
                .stream()
                .filter(reservation1 -> reservation1.getWhen().equals(reservation.getWhen()))
                .findFirst().get());
        friendlyList.remove(searchedReservation);
        System.out.println("After deleting: " + friendlyList.size());
        setupUserFriendlyGrid();
    }
}
