package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.plociennik.medicalclinicfrontend.domain.UserFriendlyReservation;
import com.plociennik.medicalclinicfrontend.logic.DateConverter;
import com.plociennik.medicalclinicfrontend.logic.SessionManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

@Component
@UIScope
public class AppointmentPage extends VerticalLayout {
    private final ApiClient apiClient;
    private SessionManager sessionManager;
    private FormLayout appointmentForm = new FormLayout();
    private Button buttonMakeAppointment = new Button("make an appointment!");
    private Grid<UserFriendlyReservation> gridUserFriendlyList = new Grid<>(UserFriendlyReservation.class);
    private ComboBox<DoctorDto> comboBoxDoctor = new ComboBox<>("Doctor");
    private Button buttonSave = new Button("Save");
    private Button buttonDelete = new Button("Delete");
    private Button buttonCancel = new Button("cancel");
    private DatePicker datePicker = new DatePicker("Day");
    private TimePicker timePicker = new TimePicker("Time");
    private HorizontalLayout buttonsTopLayout = new HorizontalLayout();
    private HorizontalLayout gridAndFormLayout = new HorizontalLayout();
    private DateConverter dateConverter = new DateConverter();

    @Autowired
    public AppointmentPage(ApiClient apiClient, SessionManager sessionManager) {
        this.apiClient = apiClient;
        this.sessionManager = sessionManager;

        buttonsTopLayout.add(buttonMakeAppointment, buttonCancel);
        gridAndFormLayout.add(gridUserFriendlyList, appointmentForm);
        gridAndFormLayout.setSizeFull();

        setupUserFriendlyGrid();
        setupAppointmentForm();
        setupButtons();

        add(buttonsTopLayout, gridAndFormLayout);
    }

    public void setupUserFriendlyGrid() {
        ArrayList<UserFriendlyReservation> friendlyList = new ArrayList<>();
        for (ReservationDto instance : sessionManager.getLoggedInUserAsPatient().getReservations()) {
            Optional<DoctorDto> searchedDoctor = apiClient.getDoctors().stream().filter(doctorDto -> doctorDto.getId().equals(instance.getDoctorId())).findFirst();
            if (searchedDoctor.isPresent()) {
                friendlyList.add(new UserFriendlyReservation(searchedDoctor.get().getName(), instance.getTime()));
            }
        }
        gridUserFriendlyList.setItems(friendlyList);
        gridUserFriendlyList.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        gridUserFriendlyList.asSingleSelect().addValueChangeListener(event -> {
            appointmentForm.setVisible(true);
            buttonCancel.setVisible(true);
        });
    }

    public void setupAppointmentForm() {
        HorizontalLayout buttons = new HorizontalLayout(buttonSave, buttonDelete);
        VerticalLayout layout = new VerticalLayout(comboBoxDoctor, datePicker, timePicker, buttons);
        comboBoxDoctor.setItems(apiClient.getDoctors());
        comboBoxDoctor.setItemLabelGenerator(DoctorDto::getName);
        timePicker.setMin("08:00");
        timePicker.setMax("18:00");
        appointmentForm.setVisible(false);
        appointmentForm.add(layout);
    }

    public void setupButtons() {
        Notification reservationAddedNotification = new Notification("The reservation has been added!", 3000);
        Notification wrongDateNotification = new Notification("You can't pick this date!",
                3000, Notification.Position.MIDDLE);
        Notification cantBookWeekendsNotification = new Notification("You can't make a reservation on weekends!",
                3000, Notification.Position.MIDDLE);
        Notification duplicateReservationNotification = new Notification("There's already a reservation at that time!",
                3000, Notification.Position.MIDDLE);
        Notification notFilledInfoNotification = new Notification("Fill all required information!",
                3000, Notification.Position.MIDDLE);
        Notification deletedReservationNotification = new Notification("The reservation has been deleted!",
                3000);
        add(reservationAddedNotification, wrongDateNotification, cantBookWeekendsNotification,
                duplicateReservationNotification, notFilledInfoNotification, deletedReservationNotification);

        buttonMakeAppointment.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonMakeAppointment.addClickListener(event -> {
            gridUserFriendlyList.asSingleSelect().clear();
            appointmentForm.setVisible(true);
            buttonCancel.setVisible(true);
        });

        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSave.addClickListener(event -> {
            if (comboBoxDoctor.getValue() == null || datePicker.getValue() == null || timePicker.getValue() == null) {
                notFilledInfoNotification.open();
            } else {
                LocalDateTime selectedDate = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
                if (selectedDate.compareTo(LocalDateTime.now()) < 1) {
                    wrongDateNotification.open();
                } else if (selectedDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || selectedDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    cantBookWeekendsNotification.open();
                } else if (checkForTheSameDate(comboBoxDoctor.getValue(), selectedDate)) {
                    duplicateReservationNotification.open();
                } else {
                    try {
                        saveEvent();
                        reservationAddedNotification.open();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDelete.addClickListener(event -> {
            UserFriendlyReservation reservation = gridUserFriendlyList.asSingleSelect().getValue();
            apiClient.deleteReservation(reservation);
            gridUserFriendlyList.deselectAll();
            setupUserFriendlyGrid();
            deletedReservationNotification.open();
        });

        buttonCancel.setVisible(false);
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonCancel.addClickListener(event -> {
            appointmentForm.setVisible(false);
            buttonCancel.setVisible(false);
        });
    }

    public void saveEvent() throws IOException {
        ReservationDto reservation = new ReservationDto();
        LocalDate date = datePicker.getValue();
        LocalTime time = timePicker.getValue();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        reservation.setTime(dateConverter.convertToString(dateTime));
        reservation.setPatientId(sessionManager.getLoggedInUserAsPatient().getId());
        reservation.setDoctorId(comboBoxDoctor.getValue().getId());

        apiClient.createReservation(reservation);

        comboBoxDoctor.setValue(null);
        datePicker.setValue(null);
        timePicker.setValue(null);
        gridUserFriendlyList.asSingleSelect().clear();
        setupUserFriendlyGrid();
    }

    public boolean checkForTheSameDate(DoctorDto doctor, LocalDateTime selectedDateTime) {
        Optional<DoctorDto> searchedDoctor = apiClient.getDoctors().stream()
                .filter(doctorDto1 -> doctorDto1.getId().equals(doctor.getId()))
                .findFirst();

        Optional<LocalDateTime> searchedReservation = searchedDoctor.get().getReservations().stream()
                .map(ReservationDto::getTime)
                .map(s -> dateConverter.convertToLocalDateTimeWithSeconds(s))
                .filter(dateTime -> dateTime.getYear() == selectedDateTime.getYear() &&
                        dateTime.getMonthValue() == selectedDateTime.getMonthValue() &&
                        dateTime.getDayOfMonth() == selectedDateTime.getDayOfMonth() &&
                        dateTime.getHour() == selectedDateTime.getHour() &&
                        dateTime.getMinute() == selectedDateTime.getMinute())
                .findFirst();
        return searchedReservation.isPresent();
    }
}
