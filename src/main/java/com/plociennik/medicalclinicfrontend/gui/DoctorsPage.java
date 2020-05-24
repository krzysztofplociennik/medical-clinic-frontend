package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class DoctorsPage extends VerticalLayout {
    private ApiClient apiClient;
    private Accordion accordion = new Accordion();

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
            Paragraph mailParagraph = new Paragraph("Mail: " + instance.getMail());
            Paragraph ratingParagraph = new Paragraph("Rating: " + instance.getRating() + "/5.0");
            Button rateButton = new Button("Rate " + instance.getName() + "!");
            rateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            verticalLayout.add(mailParagraph, ratingParagraph, rateButton);
            accordion.add(instance.getName(), verticalLayout);
        }
        accordion.close();
    }
}
