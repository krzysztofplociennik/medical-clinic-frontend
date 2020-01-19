package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AppointmentPage extends VerticalLayout {
    private Button makeAppointmentButton = new Button("make an appointment");

    public AppointmentPage() {
        add(makeAppointmentButton);
    }
}
