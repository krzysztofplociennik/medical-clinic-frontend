package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.domain.RatingDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@UIScope
public class DoctorsPage extends VerticalLayout {
    private ApiClient apiClient;
    private Accordion accordion = new Accordion();
    private PatientDto loggedPatient;
    private String[] possibleRatings = {"1 - terrible!", "2 - bad", "3 - average", "4 - good", "5 - perfect!"};
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    @Autowired
    public DoctorsPage(ApiClient apiClient) throws IOException {
        this.apiClient = apiClient;

        loggedPatient = apiClient.getPatients().stream().filter(patientDto -> patientDto.getId().equals(1L)).findFirst().get();

        setupDoctorsList();

        add(accordion);
    }

    public void setupDoctorsList() {
        for (DoctorDto doctor : apiClient.getDoctors()) {
            VerticalLayout verticalLayout = new VerticalLayout();
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            String mailValue = doctor.getMail() != null ? doctor.getMail() : "N/A";
            String ratingValue = doctor.getRating().equals("not yet rated")
                    ? doctor.getRating() : String.valueOf(doctor.getRating());

            Paragraph mailParagraph = new Paragraph("Mail: " + mailValue);
            Paragraph ratingParagraph = new Paragraph("Rating: " + ratingValue + " out of "
                    + doctor.getRatings().size() + " ratings.");

            ComboBox<String> rateComboBox = new ComboBox<>();
            rateComboBox.setPlaceholder("Choose rating");
            rateComboBox.setItems(Arrays.asList(possibleRatings));

            Button rateButton = new Button("Rate " + doctor.getName() + "!");
            rateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            rateButton.addClickListener(event -> {
                if (rateComboBox.getValue() != null && checkRatingPossibility(doctor, loggedPatient)) {
                    try {
                        addRatingEvent(doctor, Double.parseDouble(rateComboBox.getValue().substring(0, 1)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Notification.show("Thank you for your rating!");
                } else if (rateComboBox.getValue() == null) {
                    Notification.show("No rating has been selected!");
                } else {
                    Notification.show("The time between ratings have to reach 24 hours!");
                }
            });

            horizontalLayout.add(rateComboBox, rateButton);
            horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
            verticalLayout.add(mailParagraph, ratingParagraph, horizontalLayout);
            accordion.add(doctor.getName(), verticalLayout);
        }
        accordion.close();
    }

    public void addRatingEvent(DoctorDto selectedDoctor, double valueOfRating) throws IOException {
        RatingDto ratingToAdd = new RatingDto();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        ratingToAdd.setDoctorId(selectedDoctor.getId());
        ratingToAdd.setPatientId(1L);
        ratingToAdd.setValue(valueOfRating);
        ratingToAdd.setDateTime(dateTime.format(formatter));

        try {
            apiClient.addNewRating(ratingToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkRatingPossibility(DoctorDto doctor, PatientDto patient) {
        List<LocalDateTime> listOfRatings = doctor.getRatings().stream()
                .filter(ratingDto -> ratingDto.getPatientId().equals(patient.getId()))
                .map(ratingDto -> ratingDto.getDateTime())
                .map(s -> convertStringToLocalDateTime(s))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        if (listOfRatings.size() > 0) {
            if (compareTwoDates(listOfRatings.get(0))) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public LocalDateTime convertStringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }

    public boolean compareTwoDates(LocalDateTime dateTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (dateTime.getYear() == currentTime.getYear() && dateTime.getMonth() == currentTime.getMonth()) {
            if (dateTime.getDayOfMonth() == currentTime.getDayOfMonth()) {
                return false;
            } else if (currentTime.getDayOfMonth() - dateTime.getDayOfMonth() == 1) {
                LocalTime time = LocalTime.of(dateTime.getHour(), dateTime.getMinute());
                if (time.compareTo(currentTime.toLocalTime()) == -1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
