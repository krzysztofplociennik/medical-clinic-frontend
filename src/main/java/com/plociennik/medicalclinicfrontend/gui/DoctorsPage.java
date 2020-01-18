package com.plociennik.medicalclinicfrontend.gui;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.DoctorDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@SpringComponent
public class DoctorsPage extends VerticalLayout {
    @Autowired
    private ApiClient apiClient;
    private MainView mainView;

    public DoctorsPage(MainView mainView) {
        this.mainView = mainView;
        //List<DoctorDto> list = apiClient.getDoctors();

        Accordion accordion = new Accordion();

        /*for (DoctorDto instance : list) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            Text mail = new Text(instance.getMail());
            Text rating = new Text("Rating: " + instance.getRating());
            Button button = new Button("Rate " + instance.getName() + "!");
            horizontalLayout.add(mail, rating, button);
            accordion.add(instance.getName(), horizontalLayout);
        }*/
    }
}
