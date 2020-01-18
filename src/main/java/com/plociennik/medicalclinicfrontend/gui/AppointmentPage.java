package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class AppointmentPage extends VerticalLayout {
    private MainView mainView;
    private Button makeAppointmentButton = new Button("make an appointment");

    public AppointmentPage(MainView mainView) {
        this.mainView = mainView;
        add(makeAppointmentButton);
    }
}
