package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.domain.RatingDto;
import com.plociennik.medicalclinicfrontend.logic.DateConverter;
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
import java.util.*;
import java.util.stream.Collectors;

@Component
@UIScope
public class DoctorsPage extends VerticalLayout {
    private ApiClient apiClient;
    private Accordion accordion = new Accordion();
    private PatientDto loggedPatient;
    private String[] possibleRatings = {"1 - terrible!", "2 - bad", "3 - average", "4 - good", "5 - perfect!"};
    private Notification successNotification = new Notification();
    private Notification noRatingNotification = new Notification();
    private Notification tooEarlyToRateNotification = new Notification();
    private DateConverter dateConverter = new DateConverter();

    @Autowired
    public DoctorsPage(ApiClient apiClient) throws IOException {
        this.apiClient = apiClient;

        loggedPatient = apiClient.getPatients().stream().filter(p -> p.getId().equals(1L)).findFirst().get();

        setupDoctorsList();
        setupNotifications();

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
                    successNotification.open();
                } else if (rateComboBox.getValue() == null) {
                    noRatingNotification.open();
                } else {
                    tooEarlyToRateNotification.open();
                }
            });

            horizontalLayout.add(rateComboBox, rateButton);
            horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
            verticalLayout.add(mailParagraph, ratingParagraph, horizontalLayout);
            accordion.add(doctor.getName(), verticalLayout);
        }
        accordion.close();
    }

    public void setupNotifications() {
        successNotification.setText("Thank you for your rating!");
        successNotification.setDuration(3000);

        noRatingNotification.setText("You have to pick a rating!");
        noRatingNotification.setDuration(3000);
        noRatingNotification.setPosition(Notification.Position.MIDDLE);

        tooEarlyToRateNotification.setText("You can't rate that often!");
        tooEarlyToRateNotification.setDuration(3000);
        tooEarlyToRateNotification.setPosition(Notification.Position.MIDDLE);

        add(successNotification, noRatingNotification, tooEarlyToRateNotification);
    }

    public void addRatingEvent(DoctorDto selectedDoctor, double valueOfRating) throws IOException {
        RatingDto ratingToAdd = new RatingDto();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        ratingToAdd.setDoctorId(selectedDoctor.getId());
        ratingToAdd.setPatientId(loggedPatient.getId());
        ratingToAdd.setValue(valueOfRating);
        ratingToAdd.setDateTime(dateConverter.convertToString(dateTime));

        try {
            apiClient.addNewRating(ratingToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkRatingPossibility(DoctorDto doctor, PatientDto patient) {
        Optional<DoctorDto> searchedDoctor = apiClient.getDoctors().stream()
                .filter(doctorDto1 -> doctorDto1.getId().equals(doctor.getId()))
                .findFirst();

        List<LocalDateTime> listOfRatings = searchedDoctor.get().getRatings().stream()
                .filter(ratingDto -> ratingDto.getPatientId().equals(patient.getId()))
                .map(ratingDto -> ratingDto.getDateTime())
                .map(s -> dateConverter.convertToLocalDateTime(s))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        if (listOfRatings.size() > 0) {
            if (listOfRatings.get(0).compareTo(LocalDateTime.now()) < -1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
