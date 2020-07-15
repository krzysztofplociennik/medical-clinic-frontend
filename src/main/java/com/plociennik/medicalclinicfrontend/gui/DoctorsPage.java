package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@UIScope
public class DoctorsPage extends VerticalLayout {
    private ApiClient apiClient;
    private Accordion accordion = new Accordion();
    private String[] possibleRatins = {"1 - terrible!", "2 - bad", "3 - average", "4 - good", "5 - perfect!"};

    @Autowired
    public DoctorsPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        setupDoctorsList();

        add(accordion);
    }

    public void setupDoctorsList() {
        accordion.setWidth("400");
        for (DoctorDto instance : apiClient.getDoctors()) {
            VerticalLayout verticalLayout = new VerticalLayout();
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            String mailValue = instance.getMail() != null ? instance.getMail() : "";
            String ratingValue = !instance.getRating().equals("0") ? "not yet rated" : String.valueOf(instance.getRating());
            Paragraph mailParagraph = new Paragraph("Mail: " + mailValue);
            Paragraph ratingParagraph = new Paragraph("Rating: " + ratingValue);
            ComboBox<String> rateComboBox = new ComboBox<>();
            rateComboBox.setPlaceholder("Choose rating");
            rateComboBox.setItems(Arrays.asList(possibleRatins));
            Button rateButton = new Button("Rate " + instance.getName() + "!");
            rateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            horizontalLayout.add(rateComboBox, rateButton);
            horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
            verticalLayout.add(mailParagraph, ratingParagraph, horizontalLayout);
            accordion.add(instance.getName(), verticalLayout);
        }
        accordion.close();
    }
}
