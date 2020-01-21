package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
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

        for (DoctorDto instance : apiClient.getDoctors()) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            VerticalLayout verticalLayout = new VerticalLayout();
            Text mail = new Text("Mail: " + instance.getMail());
            Text rating = new Text("Rating: \n" + instance.getRating() + "/5.0");
            Button rateButton = new Button("Rate " + instance.getName() + "!");
            horizontalLayout.add(rateButton);
            verticalLayout.add(mail, rateButton, rating);
            accordion.add(instance.getName(), verticalLayout);
        }
        add(accordion);
    }
}
