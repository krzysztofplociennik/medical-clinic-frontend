package com.plociennik.medicalclinicfrontend.gui;

import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.domain.RatingDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@UIScope
public class AdminPage extends HorizontalLayout {
    private ApiClient apiClient;
    private VerticalLayout selectsLayout = new VerticalLayout();

    @Autowired
    public AdminPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        setupSelects();
    }

    public void setupSelects() {
        Paragraph introMessage = new Paragraph("Select a desired object to see the details");
        Select<PatientDto> patients = new Select<>();
        patients.addClassName("patients");
        patients.setLabel("Patients");
        patients.setItems(apiClient.getPatients().stream()
                            .filter(patientDto -> !patientDto.getUsername().equals("admin"))
                            .collect(Collectors.toList()));
        patients.setItemLabelGenerator(PatientDto::getName);

        Select<DoctorDto> doctors = new Select<>();
        doctors.addClassName("doctors");
        doctors.setLabel("Doctors");
        doctors.setItems(apiClient.getDoctors());
        doctors.setItemLabelGenerator(DoctorDto::getName);

        Select<ReservationDto> reservations = new Select<>();
        reservations.addClassName("reservations");
        reservations.setLabel("Reservations");
        reservations.setItems(apiClient.getReservations());
        reservations.setItemLabelGenerator(ReservationDto::getTime);

        Select<RatingDto> ratings = new Select<>();
        ratings.addClassName("ratings");
        ratings.setLabel("Ratings");
        ratings.setItems(apiClient.getRatings());
        ratings.setItemLabelGenerator(RatingDto::getDateTime);

        List<Select<?>> listOfSelects = Arrays.asList(patients, doctors, reservations, ratings);

        for (Select<?> select : listOfSelects) {
            select.addValueChangeListener(event -> {
                setupDetailsForm(select.getValue(), listOfSelects);
                listOfSelects.stream().forEach(s -> s.setEnabled(false));
            });
        }
        selectsLayout.add(introMessage, patients, doctors, reservations, ratings);
        add(selectsLayout);
    }

    public void setupDetailsForm(Object object, List<Select<?>> list) {
        FormLayout patientForm = new FormLayout();
        FormLayout doctorForm = new FormLayout();
        FormLayout reservationForm = new FormLayout();
        FormLayout ratingForm = new FormLayout();

        Arrays.asList(patientForm, doctorForm, reservationForm, ratingForm);

        switch (object.getClass().getSimpleName()) {
            case "PatientDto":
                if (patientForm.isVisible()) {
                    remove(patientForm);
                    FormLayout newForm = new FormLayout();
                    createPatientForm(object, newForm, list);
                } else {
                    createPatientForm(object, patientForm, list);
                }
                break;
            case "DoctorDto":
                if (doctorForm.isVisible()) {
                    remove(doctorForm);
                    FormLayout newForm = new FormLayout();
                    createDoctorForm(object, newForm, list);
                } else {
                    createDoctorForm(object, doctorForm, list);
                }
                break;
            case "ReservationDto":
                if (reservationForm.isVisible()) {
                    remove(reservationForm);
                    FormLayout newForm = new FormLayout();
                    createReservationForm(object, newForm, list);
                } else {
                    createReservationForm(object, reservationForm, list);
                }
                break;
            case "RatingDto":
                if (ratingForm.isVisible()) {
                    remove(ratingForm);
                    FormLayout newForm = new FormLayout();
                    createRatingForm(object, newForm, list);
                } else {
                    createRatingForm(object, ratingForm, list);
                }
                break;
            default:
                System.out.println("something went wrong");
        }
    }

    public void createPatientForm(Object object, FormLayout form, List<Select<?>> list) {
        TextField fieldID = new TextField("ID", String.valueOf(((PatientDto) object).getId()), "ID");
        TextField fieldName = new TextField("Name", ((PatientDto) object).getName(), "Name");
        TextField fieldMail = new TextField("Mail", ((PatientDto) object).getMail(), "Mail");
        TextField fieldPhone = new TextField("Phone number", ((PatientDto) object).getPhoneNumber(), "Phone number");
        TextField fieldUsername = new TextField("Username", ((PatientDto) object).getUsername(), "Username");
        TextField fieldPassword = new TextField("Password", ((PatientDto) object).getPassword(), "Password");

        Arrays.asList(fieldID, fieldName, fieldMail, fieldPhone, fieldUsername, fieldPassword).stream()
                .forEach(textField -> textField.setReadOnly(true));

        Button buttonDelete = new Button("delete", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
            try {
                deleteSelectedItem(object);
                reloadPage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button buttonCancel = new Button("cancel", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
        });

        HorizontalLayout firstLineOfDataLayout = new HorizontalLayout(fieldID, fieldName, fieldMail);
        HorizontalLayout secondLineOfDataLayout = new HorizontalLayout(fieldPhone, fieldUsername, fieldPassword);
        HorizontalLayout buttonsLayout = new HorizontalLayout(buttonDelete, buttonCancel);
        VerticalLayout verticalDetailsFormLayout = new VerticalLayout(firstLineOfDataLayout, secondLineOfDataLayout, buttonsLayout);
        form.add(verticalDetailsFormLayout);
        form.setVisible(true);
        add(form);
    }

    public void createDoctorForm(Object object, FormLayout form, List<Select<?>> list) {
        TextField fieldID = new TextField("ID", String.valueOf(((DoctorDto) object).getId()), "ID");
        TextField fieldName = new TextField("Name", ((DoctorDto) object).getName(), "Name");
        TextField fieldMail = new TextField("Mail", ((DoctorDto) object).getMail(), "Mail");
        TextField fieldRating = new TextField("Rating", ((DoctorDto) object).getRating(), "Rating");

        Arrays.asList(fieldID, fieldName, fieldMail, fieldRating).stream().forEach(textField -> textField.setReadOnly(true));

        Button buttonDelete = new Button("delete", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
            try {
                deleteSelectedItem(object);
                reloadPage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button buttonCancel = new Button("cancel", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
        });

        HorizontalLayout firstLineOfDataLayout = new HorizontalLayout(fieldID, fieldName);
        HorizontalLayout secondLineOfDataLayout = new HorizontalLayout(fieldMail, fieldRating);
        HorizontalLayout buttonsLayout = new HorizontalLayout(buttonDelete, buttonCancel);
        VerticalLayout verticalDetailsFormLayout = new VerticalLayout(firstLineOfDataLayout, secondLineOfDataLayout, buttonsLayout);
        form.add(verticalDetailsFormLayout);
        form.setVisible(true);
        add(form);
    }

    public void createReservationForm(Object object, FormLayout form, List<Select<?>> list) {
        TextField fieldID = new TextField("ID", String.valueOf(((ReservationDto) object).getId()), "ID");
        TextField fieldTime = new TextField("Time", ((ReservationDto) object).getTime(), "Time");
        TextField fieldPatientId = new TextField("Patient ID", String.valueOf(((ReservationDto) object).getPatientId()), "Patient ID");
        TextField fieldDoctorId = new TextField("Doctor ID", String.valueOf(((ReservationDto) object).getDoctorId()), "Doctor ID");

        Arrays.asList(fieldID, fieldTime, fieldPatientId, fieldDoctorId).stream().forEach(textField -> textField.setReadOnly(true));

        Button buttonDelete = new Button("delete", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
            try {
                deleteSelectedItem(object);
                reloadPage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button buttonCancel = new Button("cancel", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
        });

        HorizontalLayout firstLineOfDataLayout = new HorizontalLayout(fieldID, fieldTime);
        HorizontalLayout secondLineOfDataLayout = new HorizontalLayout(fieldPatientId, fieldDoctorId);
        HorizontalLayout buttonsLayout = new HorizontalLayout(buttonDelete, buttonCancel);
        VerticalLayout verticalDetailsFormLayout = new VerticalLayout(firstLineOfDataLayout, secondLineOfDataLayout, buttonsLayout);
        form.add(verticalDetailsFormLayout);
        form.setVisible(true);
        add(form);
    }

    public void createRatingForm(Object object, FormLayout form, List<Select<?>> list) {
        TextField fieldID = new TextField("ID", String.valueOf(((RatingDto) object).getId()), "ID");
        TextField fieldValue = new TextField("Value", String.valueOf(((RatingDto) object).getValue()), "Value");
        TextField fieldPatientId = new TextField("Patient ID", String.valueOf(((RatingDto) object).getPatientId()), "Patient ID");
        TextField fieldDoctorId = new TextField("Doctor ID", String.valueOf(((RatingDto) object).getDoctorId()), "Doctor ID");
        TextField fieldTime = new TextField("Time", String.valueOf(((RatingDto) object).getValue()), "Time");

        Arrays.asList(fieldID, fieldValue, fieldPatientId, fieldDoctorId, fieldTime).stream().forEach(textField -> textField.setReadOnly(true));

        Button buttonDelete = new Button("delete", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
            try {
                deleteSelectedItem(object);
                reloadPage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        buttonDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button buttonCancel = new Button("cancel", event -> {
            form.setVisible(false);
            list.stream().forEach(select -> select.setEnabled(true));
        });

        HorizontalLayout firstLineOfDataLayout = new HorizontalLayout(fieldID, fieldTime, fieldValue);
        HorizontalLayout secondLineOfDataLayout = new HorizontalLayout(fieldPatientId, fieldDoctorId);
        HorizontalLayout buttonsLayout = new HorizontalLayout(buttonDelete, buttonCancel);
        VerticalLayout verticalDetailsFormLayout = new VerticalLayout(firstLineOfDataLayout, secondLineOfDataLayout, buttonsLayout);
        form.add(verticalDetailsFormLayout);
        form.setVisible(true);
        add(form);
    }

    public void deleteSelectedItem(Object object) {
        switch (object.getClass().getSimpleName()) {
            case "PatientDto":
                apiClient.deletePatient((PatientDto) object);
                Notification.show("Patient has been deleted\nThe page will now reload", 3000, Notification.Position.BOTTOM_CENTER);
                break;
            case "DoctorDto":
                apiClient.deleteDoctor((DoctorDto) object);
                Notification.show("Doctor has been deleted\nThe page will now reload", 3000, Notification.Position.BOTTOM_CENTER);
                break;
            case "ReservationDto":
                apiClient.deleteReservation((ReservationDto) object);
                Notification.show("Reservation has been deleted\nThe page will now reload", 3000, Notification.Position.BOTTOM_CENTER);
                break;
            case "RatingDto":
                apiClient.deleteRating((RatingDto) object);
                Notification.show("Rating has been deleted\nThe page will now reload", 3000, Notification.Position.BOTTOM_CENTER);
                break;
            default:
                Notification.show("Something went wrong.", 3000, Notification.Position.MIDDLE);
        }
    }

    public void reloadPage() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(5);
//        getUI().get().getPage().reload();
    }
}
