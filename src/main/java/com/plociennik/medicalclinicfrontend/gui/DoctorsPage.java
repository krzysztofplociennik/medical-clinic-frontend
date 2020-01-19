package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Route("doctors")
@Component
@UIScope
public class DoctorsPage extends VerticalLayout {
    private ApiClient apiClient;

    @Autowired
    public DoctorsPage(ApiClient apiClient) {
        this.apiClient = apiClient;

        List<DoctorDto> list = apiClient.getDoctors();

        Accordion accordion = new Accordion();

        for (DoctorDto instance : list) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            Text mail = new Text(instance.getMail());
            Text rating = new Text("Rating: " + instance.getRating());
            Button button = new Button("Rate " + instance.getName() + "!");
            horizontalLayout.add(mail, rating, button);
            accordion.add(instance.getName(), horizontalLayout);
        }
        add(accordion);
    }
}
