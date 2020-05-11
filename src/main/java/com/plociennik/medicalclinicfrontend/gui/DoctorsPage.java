package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class DoctorsPage extends VerticalLayout {
    private ApiClient apiClient;

    @Autowired
    public DoctorsPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        Accordion accordion = new Accordion();

        accordion.setWidth("400");

        for (DoctorDto instance : apiClient.getDoctors()) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            VerticalLayout verticalLayout = new VerticalLayout();
            VerticalLayout mailVerticalLayout = new VerticalLayout();
            Text mail = new Text("Mail: \n " + instance.getMail());
            Text rating = new Text("   Rating: \n" + instance.getRating() + "/5.0");
            Button rateButton = new Button("Rate " + instance.getName() + "!");
            rateButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            horizontalLayout.add(rateButton);
            mailVerticalLayout.add(mail);
            verticalLayout.add(mailVerticalLayout, rating, rateButton);
            accordion.add(instance.getName(), verticalLayout);
        }

        add(accordion);
        accordion.close();
    }
}
